package le.data.scs.spider.crawler.thread.tmall;

import le.data.scs.common.entity.meta.chat.ChatLog;
import le.data.scs.common.entity.meta.tmall.TMallSeedEntity;
import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.crawler.crawler.common.Crawler;
import le.data.scs.spider.crawler.crawler.common.CrawlerFactory;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserFactory;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterFactory;
import le.data.scs.spider.thread.AbstractProcess;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public class TmallChatCrawlerThread extends AbstractProcess<TMallSeedEntity> {
    public TmallChatCrawlerThread(String seedQueueName, String errorQueueName, Class<TMallSeedEntity> tMallSeedEntityClass) {
        super(seedQueueName, errorQueueName, tMallSeedEntityClass);
    }

    public TmallChatCrawlerThread() throws SpiderConfigException {
        super(ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.seed.tmall"), ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.error.tmall"), TMallSeedEntity.class);
    }

    @Override
    public void process(TMallSeedEntity tMallSeedEntity) throws Exception {
        Crawler<TMallSeedEntity> crawler = CrawlerFactory.getCrawler(ConfigFactory.getProperty("spider.crawler.chat.tmall.detail.classname"));
        Parser<ChatLog, TMallSeedEntity> parser = ParserFactory.getParser(ConfigFactory.getProperty("spider.parser.chat.tmall.detail.classname"));
        Persister<ChatLog, TMallSeedEntity> persister = PersisterFactory.getPersister(ConfigFactory.getProperty("spider.persister.chat.tmall.detail.classname"));
        CrawlerEntity<TMallSeedEntity> crawlerEntity = null;
        ParserEntity<ChatLog> parserEntity = null;
        if (tMallSeedEntity != null) {
            crawlerEntity = crawler.crawlByGet(tMallSeedEntity);
            parserEntity = parser.parser(crawlerEntity);
            persister.persist(parserEntity,tMallSeedEntity);
        }
    }
}
