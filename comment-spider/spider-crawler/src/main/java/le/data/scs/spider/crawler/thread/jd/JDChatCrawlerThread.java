package le.data.scs.spider.crawler.thread.jd;

import le.data.scs.common.entity.meta.chat.ChatLog;
import le.data.scs.common.entity.meta.jd.JDSeedEntity;
import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.distribution.mq.MessageQueue;
import le.data.scs.spider.crawler.crawler.common.Crawler;
import le.data.scs.spider.crawler.crawler.common.CrawlerFactory;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserFactory;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterFactory;
import le.data.scs.spider.thread.AbstractProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class JDChatCrawlerThread extends AbstractProcess<JDSeedEntity> {

    private static final Logger log = LoggerFactory.getLogger(JDChatCrawlerThread.class);

    public JDChatCrawlerThread(String seedQueueName, String errorQueueName, Class<JDSeedEntity> jdSeedEntityClass, MessageQueue<JDSeedEntity> queue) {
        super(seedQueueName, errorQueueName, jdSeedEntityClass);
    }

    public JDChatCrawlerThread() throws SpiderConfigException {
        super(ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.seed.jd"), ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.error.jd"), JDSeedEntity.class);
    }

    public void process(JDSeedEntity seedEntity) throws Exception {
        Crawler<JDSeedEntity> crawler = CrawlerFactory.getCrawler(ConfigFactory.getProperty("spider.crawler.chat.jd.classname"));
        Parser<List<ChatLog>, JDSeedEntity> parser = ParserFactory.getParser(ConfigFactory.getProperty("spider.parser.chat.jd.detail.classname"));
        Persister<List<ChatLog>, JDSeedEntity> persister = PersisterFactory.getPersister(ConfigFactory.getProperty("spider.persist.chat.jd.classname"));
        CrawlerEntity<JDSeedEntity> crawlerEntity = null;
        ParserEntity<List<ChatLog>> parserEntity = null;
        if (seedEntity != null) {
            crawlerEntity = crawler.crawlByGet(seedEntity);
            parserEntity = parser.parser(crawlerEntity);
            persister.persist(parserEntity, seedEntity);
        }
    }
}
