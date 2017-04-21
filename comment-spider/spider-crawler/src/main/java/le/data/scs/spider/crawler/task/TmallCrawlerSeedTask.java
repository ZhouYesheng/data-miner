package le.data.scs.spider.crawler.task;

import le.data.scs.common.entity.meta.tmall.TMallCustomerEntity;
import le.data.scs.common.entity.meta.tmall.TMallNickEntity;
import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.distribution.cache.CacheException;
import le.data.scs.spider.distribution.mq.MQException;
import le.data.scs.spider.distribution.mq.MQFactory;
import le.data.scs.spider.distribution.mq.MessageQueue;
import le.data.scs.spider.distribution.task.DistributedTaskException;
import le.data.scs.spider.distribution.task.leadertask.ZKDistributedLeaderTaskAdapter;
import le.data.scs.spider.crawler.cache.SpiderCache;
import le.data.scs.spider.crawler.crawler.common.Crawler;
import le.data.scs.spider.crawler.crawler.common.CrawlerFactory;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.entity.common.SpiderStatus;

import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserFactory;
import le.data.scs.spider.utils.MD5;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TmallCrawlerSeedTask extends ZKDistributedLeaderTaskAdapter {

    private static final long one_day = 1000 * 60 * 60 * 24 * 1;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger log = LoggerFactory.getLogger(TmallCrawlerSeedTask.class);

    private MessageQueue<TMallCustomerEntity> queue;

    public TmallCrawlerSeedTask() {
        String queueName = null;
        try {
            queueName = ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.seed.tmall");
            this.queue = MQFactory.getMessageQueue(queueName);
        } catch (SpiderConfigException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Object task() throws Exception {
        TMallNickEntity tMallNickEntity = new TMallNickEntity();
        String start = dateFormat.format(new Date(System.currentTimeMillis() - one_day));
        String beforeCacheStart = dateFormat.format(new Date(System.currentTimeMillis() - one_day*2));
        String end = start;
        tMallNickEntity.setNickUrl(ConfigFactory.getProperty("spider.crawler.tmall.nickname.url"));
        tMallNickEntity.setStart(start);
        tMallNickEntity.setEnd(end);
        String cacheKey = CrawlerType.TMALL.toString() + "_" + start + "_" + end;
        String beforeCacheKey = CrawlerType.TMALL.toString() + "_" + beforeCacheStart + "_" + beforeCacheStart;
        SpiderCache.getCache(CrawlerType.TMALL).del(beforeCacheKey);
        String result = null;
        if (SpiderCache.getCache(CrawlerType.TMALL).exist(cacheKey)) {
            result = SpiderCache.getCache(CrawlerType.TMALL).get(cacheKey,String.class);
            if(SpiderStatus.FINISHED.toString().equals(result)) {
                log.info(cacheKey + " push seed finished " + new Date());
                ConfigFactory.sleepTime();
                return null;
            }
        }else{
            SpiderCache.getCache(CrawlerType.TMALL).put(cacheKey, SpiderStatus.RUNNING.toString());
        }
        List<TMallCustomerEntity> nickNames = getNickNames(tMallNickEntity);
        if (CollectionUtils.isEmpty(nickNames))
            return null;
        List<TMallCustomerEntity> seeds = null;
        for (TMallCustomerEntity waiters : nickNames) {
            if (!existNick(waiters,cacheKey)) {
                seeds = getTmallSeeds(waiters);
                if (!CollectionUtils.isEmpty(seeds)) {
                    pushSeeds(seeds);
                }
                putCache(waiters,cacheKey);
                ConfigFactory.sleepTime();
            }else{
                log.info("waiter "+waiters.getQueryEntity().getEmployeeNick()+" is processed date is "+waiters.getQueryEntity().getStart()+"_"+waiters.getQueryEntity().getEnd());
            }
        }
        SpiderCache.getCache(CrawlerType.TMALL).put(cacheKey, SpiderStatus.FINISHED.toString());
        return null;
    }

    private boolean existNick(TMallCustomerEntity tMallCustomerEntity,String parent) {
        String cacheKey = parent+"/"+MD5.md5(CrawlerType.TMALL.toString() + "_" + tMallCustomerEntity.getQueryEntity().getStart() + "_" + tMallCustomerEntity.getQueryEntity().getEnd() + "_" + tMallCustomerEntity.getQueryEntity().getEmployeeNick());
        try {
            return SpiderCache.getCache(CrawlerType.TMALL).exist(cacheKey);
        } catch (CacheException e) {
            return false;
        }
    }

    private void putCache(TMallCustomerEntity tMallCustomerEntity,String parent) {
        String cacheKey = parent+"/"+MD5.md5(CrawlerType.TMALL.toString() + "_" + tMallCustomerEntity.getQueryEntity().getStart() + "_" + tMallCustomerEntity.getQueryEntity().getEnd() + "_" + tMallCustomerEntity.getQueryEntity().getEmployeeNick());
        try {
            SpiderCache.getCache(CrawlerType.TMALL).put(cacheKey, "finished");
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    private void pushSeeds(List<TMallCustomerEntity> seeds) throws MQException {
        this.queue.offer(seeds);
    }

    @Override
    protected Object change() {
        return null;
    }

    private List<TMallCustomerEntity> getTmallSeeds(TMallCustomerEntity waiters) {
        int count = 0;
        while (count < 3) {
            try {
                Crawler<TMallCustomerEntity> crawler = CrawlerFactory.getCrawler(ConfigFactory.getProperty("spider.crawler.chat.tmall.customer.classname"));
                Parser<List<TMallCustomerEntity>, TMallCustomerEntity> parser = ParserFactory.getParser(ConfigFactory.getProperty("spider.parser.chat.tmall.customer.classname"));
                CrawlerEntity<TMallCustomerEntity> crawlerEntity = crawler.crawlByGet(waiters);
                ParserEntity<List<TMallCustomerEntity>> parserEntity = parser.parser(crawlerEntity);
                return parserEntity.getData();
            } catch (Exception e) {
                count++;
                log.error("getTmallSeeds crawler error [" + count + "]");
                e.printStackTrace();
                ConfigFactory.sleepTime();
            }
        }
        return null;
    }

    private List<TMallCustomerEntity> getNickNames(TMallNickEntity tMallNickEntity) {
        int count = 0;
        while (count < 3) {
            try {
                Crawler<TMallNickEntity> crawler = CrawlerFactory.getCrawler(ConfigFactory.getProperty("spider.crawler.chat.tmall.nick.classname"));
                CrawlerEntity<TMallNickEntity> crawlerEntity = crawler.crawlByGet(tMallNickEntity);
                Parser<List<TMallCustomerEntity>, TMallNickEntity> parser = ParserFactory.getParser(ConfigFactory.getProperty("spider.parser.chat.tmall.nick.classname"));
                ParserEntity<List<TMallCustomerEntity>> parserEntity = parser.parser(crawlerEntity);
                return parserEntity.getData();
            } catch (Exception e) {
                count++;
                log.error("getNickNames crawler error [" + count + "]");
                e.printStackTrace();
                ConfigFactory.sleepTime();
            }
        }
        return null;
    }

    public static void main(String[] args) throws DistributedTaskException {
        ZKDistributedLeaderTaskAdapter task = new TmallCrawlerSeedTask();
        task.runTask(true);
    }
}
