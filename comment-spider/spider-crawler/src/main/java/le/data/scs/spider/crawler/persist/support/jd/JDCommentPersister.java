package le.data.scs.spider.crawler.persist.support.jd;

import le.data.scs.common.entity.meta.comment.CommentLog;
import le.data.scs.common.entity.meta.jd.JDCommentEntity;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterException;
import le.data.scs.spider.crawler.persist.support.mysql.PersisterDao;

/**
 * Created by yangyong3 on 2017/3/8.
 * 京东评论存储工具
 */
public class JDCommentPersister implements Persister<CommentLog, JDCommentEntity> {

    @Override
    public void persist(ParserEntity<CommentLog> parserEntity, JDCommentEntity jdCommentEntity) throws PersisterException {
        try {
            PersisterDao.saveComments(parserEntity.getData(), CrawlerType.JD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
