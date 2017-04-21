package le.data.scs.spider.crawler.thread.jd;

import le.data.scs.common.entity.meta.comment.CommentLog;
import le.data.scs.common.entity.meta.jd.JDCommentEntity;
import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.crawler.crawler.common.Crawler;
import le.data.scs.spider.crawler.crawler.common.CrawlerFactory;
import le.data.scs.spider.crawler.crawler.support.jd.JDCommentsCrawler;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserFactory;
import le.data.scs.spider.crawler.parser.support.jd.JDCommentsParser;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterFactory;
import le.data.scs.spider.crawler.persist.support.jd.JDCommentPersister;
import le.data.scs.spider.thread.AbstractProcess;


/**
 * Created by yangyong3 on 2017/3/8.
 */
public class JDCommentCrawlerThread extends AbstractProcess<JDCommentEntity> {
    public JDCommentCrawlerThread(String seedQueueName, String errorQueueName, Class<JDCommentEntity> jdCommentEntityClass) {
        super(seedQueueName, errorQueueName, jdCommentEntityClass);
    }

    public JDCommentCrawlerThread() throws SpiderConfigException {
        super(ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.seed.comment.jd"), ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.error.comment.jd"), JDCommentEntity.class);
    }

    @Override
    public void process(JDCommentEntity commentEntity) throws Exception {
        Crawler<JDCommentEntity> crawler = CrawlerFactory.getCrawler(JDCommentsCrawler.class.getName());
        Parser<CommentLog,JDCommentEntity> parser = ParserFactory.getParser(JDCommentsParser.class.getName());
        Persister<CommentLog,JDCommentEntity> persister = PersisterFactory.getPersister(JDCommentPersister.class.getName());
        CrawlerEntity<JDCommentEntity> crawlerEntity = crawler.crawlByGet(commentEntity);
        ParserEntity<CommentLog> parserEntity = parser.parser(crawlerEntity);
        persister.persist(parserEntity,commentEntity);
    }
}
