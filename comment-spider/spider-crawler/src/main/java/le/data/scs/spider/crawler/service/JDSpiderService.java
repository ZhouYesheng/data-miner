package le.data.scs.spider.crawler.service;

import le.data.scs.common.entity.meta.comment.CommentLog;
import le.data.scs.common.entity.meta.jd.JDCommentEntity;
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
import le.data.scs.spider.crawler.crawler.support.jd.JDCommentsCrawler;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserException;
import le.data.scs.spider.crawler.parser.ParserFactory;
import le.data.scs.spider.crawler.parser.support.jd.JDCommentsParser;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterFactory;
import le.data.scs.spider.crawler.persist.support.jd.JDCommentPersister;
import le.data.scs.spider.utils.JsonUtils;
import le.data.scs.spider.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by yangyong3 on 2017/3/10.
 */
public class JDSpiderService {

    private static final Logger log = LoggerFactory.getLogger(JDSpiderService.class);

    private MessageQueue<JDCommentEntity> queue;

    public JDSpiderService() {
        try {
            String queueName = ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + ConfigFactory.getProperty("spider.crawler.queue.seed.comment.jd");
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

    public void jdCommentSeedService(JDCommentEntity commentEntity) throws IllegalAccessException, ClassNotFoundException, InstantiationException, CrawlerException, ParserException, MQException, IOException {
        if (StringUtils.isBlank(commentEntity.getMd5())){
            commentEntity.setMd5(MD5.md5(commentEntity.getPageUrl()));
        }
        Crawler<JDCommentEntity> crawler = CrawlerFactory.getCrawler(JDCommentsCrawler.class.getName());
        Parser<CommentLog,JDCommentEntity> parser = ParserFactory.getParser(JDCommentsParser.class.getName());
        CrawlerEntity<JDCommentEntity> crawlerEntity = crawler.crawlByGet(commentEntity);
        ParserEntity<CommentLog> parserEntity = parser.parser(crawlerEntity);
        if(parserEntity.getPage()!=null){
            for(int i=1;i<=parserEntity.getPage().getTotalPage();i++){
                commentEntity.setPage(i);
                queue.offer(commentEntity);
            }
            PersisterDao.updateCommentSeed(commentEntity.getMd5(),1);
        }
    }

    public void saveCommentSeed(JDCommentEntity commentEntity) throws IllegalAccessException, ClassNotFoundException, InstantiationException, CrawlerException, ParserException, MQException, IOException {
        if (StringUtils.isBlank(commentEntity.getMd5())){
            commentEntity.setMd5(MD5.md5(commentEntity.getPageUrl()));
        }
        if(!PersisterDao.existCommentSeed(commentEntity.getMd5())){
            PersisterDao.saveCommentSeed(commentEntity.getMd5(),commentEntity.getPageUrl(), JsonUtils.toJson(commentEntity), CrawlerType.JD.toString());
        }
    }
}
