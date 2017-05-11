package le.data.scs.spider.crawler.thread.tmall;

import le.data.scs.common.entity.meta.comment.CommentLog;
import le.data.scs.common.entity.meta.tmall.TMallCommentEntity;
import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.crawler.crawler.common.Crawler;
import le.data.scs.spider.crawler.crawler.common.CrawlerFactory;
import le.data.scs.spider.crawler.crawler.support.tmall.TmallCommentCrawler;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserFactory;
import le.data.scs.spider.crawler.parser.support.tmall.TmallCommentParser;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterFactory;
import le.data.scs.spider.crawler.persist.support.tmall.TmallCommentPersister;
import le.data.scs.spider.thread.AbstractProcess;


/**
 * Created by yangyong3 on 2017/3/9.
 * 天猫评论种子抓取线程
 */
public class TmallCommentCrawlerThread extends AbstractProcess<TMallCommentEntity> {
    public TmallCommentCrawlerThread(String seedQueueName, String errorQueueName, Class<TMallCommentEntity> tMallCommentEntityClass) {
        super(seedQueueName, errorQueueName, tMallCommentEntityClass);
    }

    public TmallCommentCrawlerThread() throws SpiderConfigException {
        super(ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.seed.comment.tmall"), ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.error.comment.tmall"), TMallCommentEntity.class);
    }

    @Override
    public void process(TMallCommentEntity tMallCommentEntity) throws Exception {
        Crawler<TMallCommentEntity> crawler = CrawlerFactory.getCrawler(TmallCommentCrawler.class);
        Parser<CommentLog, TMallCommentEntity> commentParser = ParserFactory.getParser(TmallCommentParser.class);
        Persister<CommentLog, TMallCommentEntity> persister = PersisterFactory.getPersister(TmallCommentPersister.class);
        CrawlerEntity<TMallCommentEntity> crawlerEntity = crawler.crawlByGet(tMallCommentEntity);
        ParserEntity<CommentLog> commentLogParserEntity = commentParser.parser(crawlerEntity);
        persister.persist(commentLogParserEntity, tMallCommentEntity);
    }
}
