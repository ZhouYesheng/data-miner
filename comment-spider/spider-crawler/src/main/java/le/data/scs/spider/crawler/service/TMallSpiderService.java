package le.data.scs.spider.crawler.service;

import le.data.scs.common.entity.meta.comment.CommentLog;
import le.data.scs.common.entity.meta.tmall.TMallCommentEntity;
import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.persist.support.mysql.PersisterDao;
import le.data.scs.spider.distribution.mq.MQException;
import le.data.scs.spider.distribution.mq.MQFactory;
import le.data.scs.spider.distribution.mq.MessageQueue;
import le.data.scs.spider.crawler.crawler.common.Crawler;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.crawler.crawler.common.CrawlerFactory;
import le.data.scs.spider.crawler.crawler.support.tmall.TmallCommentCrawler;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.PageEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserException;
import le.data.scs.spider.crawler.parser.ParserFactory;
import le.data.scs.spider.crawler.parser.support.tmall.TmallCommentParser;
import le.data.scs.spider.crawler.parser.support.tmall.TmallTagParser;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterFactory;
import le.data.scs.spider.crawler.persist.support.tmall.TmallCommentPersister;
import le.data.scs.spider.utils.JsonUtils;
import le.data.scs.spider.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by yangyong3 on 2017/3/10.
 */
public class TMallSpiderService {

    private static final Logger log = LoggerFactory.getLogger(TMallSpiderService.class);

    private MessageQueue<TMallCommentEntity> queue;

    public TMallSpiderService() {
        try {
            String queueName = ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.seed.comment.tmall");
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

    public void tmallCommentSeedService(TMallCommentEntity tMallCommentEntity) throws IllegalAccessException, InstantiationException, ClassNotFoundException, CrawlerException, ParserException, MQException, IOException {
        if (StringUtils.isBlank(tMallCommentEntity.getMd5())) {
            tMallCommentEntity.setMd5(MD5.md5(tMallCommentEntity.getUrl()));
        }
        Crawler<TMallCommentEntity> crawler = CrawlerFactory.getCrawler(TmallCommentCrawler.class);
        Parser<List<String>, TMallCommentEntity> tagsParser = ParserFactory.getParser(TmallTagParser.class);
        Parser<CommentLog, TMallCommentEntity> commentParser = ParserFactory.getParser(TmallCommentParser.class);
        Persister<CommentLog, TMallCommentEntity> persister = PersisterFactory.getPersister(TmallCommentPersister.class);
        CrawlerEntity<TMallCommentEntity> crawlerEntity = crawler.crawlByGet(tMallCommentEntity);
        ParserEntity<List<String>> parserEntityTags = tagsParser.parser(crawlerEntity);
        tMallCommentEntity.setTags(parserEntityTags.getData());
        tMallCommentEntity.setType(TMallCommentEntity.TMallCommentType.COMMENT_DETAIL.toString());
        CrawlerEntity<TMallCommentEntity> commentEntityCrawlerEntity = crawler.crawlByGet(tMallCommentEntity);
        ParserEntity<CommentLog> commentLogParserEntity = commentParser.parser(commentEntityCrawlerEntity);
        PageEntity pageEntity = commentLogParserEntity.getPage();
        for (int i = 1; i <= pageEntity.getTotalPage(); i++) {
            tMallCommentEntity.setCurrentPage(i);
            queue.offer(tMallCommentEntity);
        }
        PersisterDao.updateCommentSeed(tMallCommentEntity.getMd5(),1);
    }

    public void saveCommentSeed(TMallCommentEntity tMallCommentEntity) throws IllegalAccessException, InstantiationException, ClassNotFoundException, CrawlerException, ParserException, MQException, IOException {
        if (StringUtils.isBlank(tMallCommentEntity.getMd5())) {
            tMallCommentEntity.setMd5(MD5.md5(tMallCommentEntity.getUrl()));
        }
        if (!PersisterDao.existCommentSeed(tMallCommentEntity.getMd5())) {
            PersisterDao.saveCommentSeed(tMallCommentEntity.getMd5(), tMallCommentEntity.getUrl(), JsonUtils.toJson(tMallCommentEntity), CrawlerType.TMALL.toString());
        }
    }

}
