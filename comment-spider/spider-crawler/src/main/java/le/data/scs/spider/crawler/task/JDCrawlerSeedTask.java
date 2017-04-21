package le.data.scs.spider.crawler.task;

import le.data.scs.common.entity.meta.jd.JDSeedEntity;
import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.distribution.cache.CacheException;
import le.data.scs.spider.distribution.mq.MQException;
import le.data.scs.spider.distribution.mq.MQFactory;
import le.data.scs.spider.distribution.mq.MessageQueue;
import le.data.scs.spider.distribution.task.DistributedTaskException;
import le.data.scs.spider.distribution.task.leadertask.LeaderTask;
import le.data.scs.spider.distribution.task.leadertask.ZKDistributedLeaderTaskAdapter;
import le.data.scs.spider.crawler.cache.SpiderCache;
import le.data.scs.spider.crawler.crawler.common.Crawler;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.crawler.crawler.common.CrawlerFactory;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.entity.common.SpiderStatus;

import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserException;
import le.data.scs.spider.crawler.parser.ParserFactory;
import le.data.scs.spider.crawler.persist.PersisterException;
import le.data.scs.spider.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangyong3 on 2017/2/23.
 * 按照分页将京东前一天的所有聊天记录拆分作为种子发送到分布式消息队列中
 */
public class JDCrawlerSeedTask extends ZKDistributedLeaderTaskAdapter {

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private static final long one_day = 1000 * 60 * 60 * 24 * 1;

    private static final Logger log = LoggerFactory.getLogger(JDCrawlerSeedTask.class);

    private MessageQueue<JDSeedEntity> queue;

    public JDCrawlerSeedTask() {
        try {
            String queueName = ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.seed.jd");
            this.queue = MQFactory.getMessageQueue(queueName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SpiderConfigException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected Object task() throws DistributedTaskException, ParserException, MQException, CacheException, SpiderConfigException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        String start = format.format(new Date(System.currentTimeMillis() - one_day));
        String beforeCacheStart = format.format(new Date(System.currentTimeMillis() - one_day * 2));
        String end = start;
        String cache_key = CrawlerType.JD.toString() + "_" + start + "_" + end;
        String beforeCacheKey = CrawlerType.JD.toString() + "_" + beforeCacheStart + "_" + beforeCacheStart;
        SpiderCache.getCache(CrawlerType.JD).del(beforeCacheKey);
        String result = null;
        if (SpiderCache.getCache(CrawlerType.JD).exist(cache_key)) {
            result = SpiderCache.getCache(CrawlerType.JD).get(cache_key, String.class);
            if (SpiderStatus.FINISHED.toString().equals(result)) {
                log.info(cache_key + " push seed finished " + new Date());
                ConfigFactory.sleepTime();
                return null;
            }
        } else {
            SpiderCache.getCache(CrawlerType.JD).put(cache_key, SpiderStatus.RUNNING.toString());
        }
        JDSeedEntity seedEntity = new JDSeedEntity(1, 100, start, end);
        Crawler<JDSeedEntity> crawler = CrawlerFactory.getCrawler(ConfigFactory.getProperty("spider.crawler.chat.jd.classname"));
        Parser<String, JDSeedEntity> parser = ParserFactory.getParser(ConfigFactory.getProperty("spider.parser.chat.jd.seed.classname"));
        CrawlerEntity<JDSeedEntity> crawlerEntity = null;
        int count = 0;
        while (count < 3) {
            try {
                crawlerEntity = crawler.crawlByGet(seedEntity);
                break;
            } catch (Exception e) {
                count++;
                System.out.println("第" + count + "次抓取失败");
                e.printStackTrace();
                ConfigFactory.sleepTime();
            }
        }
        ParserEntity<String> parserEntity = parser.parser(crawlerEntity);
        for (int i = 1; i <= parserEntity.getPage().getTotalPage(); i++) {
            seedEntity.setPage(i);
            if (!isExist(seedEntity, cache_key)) {
                queue.offer(seedEntity);
                putCache(seedEntity, cache_key);
            }
        }
        SpiderCache.getCache(CrawlerType.JD).put(cache_key, SpiderStatus.FINISHED.toString());
        return null;
    }

    private void putCache(JDSeedEntity seedEntity, String parent) {
        String key = parent + "/" + MD5.md5(CrawlerType.JD + "_seed_" + seedEntity.getStartTime() + "_" + seedEntity.getEndTime() + "_" + seedEntity.getPage() + "_" + seedEntity.getPageSize());
        try {
            SpiderCache.getCache(CrawlerType.JD).put(key, "finished");
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }


    private boolean isExist(JDSeedEntity seedEntity, String parent) {
        String key = parent + "/" + MD5.md5(CrawlerType.JD + "_seed_" + seedEntity.getStartTime() + "_" + seedEntity.getEndTime() + "_" + seedEntity.getPage() + "_" + seedEntity.getPageSize());
        try {
            return SpiderCache.getCache(CrawlerType.JD).exist(key);
        } catch (CacheException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected Object change() {
        return null;
    }

    public static void main(String[] args) throws CrawlerException, ParserException, PersisterException, InterruptedException, SpiderConfigException, MQException, DistributedTaskException {
        LeaderTask task = new JDCrawlerSeedTask();
        task.runTask(true);
    }
}
