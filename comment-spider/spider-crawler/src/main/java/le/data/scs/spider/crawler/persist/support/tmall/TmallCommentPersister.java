package le.data.scs.spider.crawler.persist.support.tmall;

import le.data.scs.common.entity.meta.comment.CommentLog;
import le.data.scs.common.entity.meta.tmall.TMallCommentEntity;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterException;
import le.data.scs.spider.crawler.persist.support.mysql.PersisterDao;

/**
 * Created by yangyong3 on 2017/3/8.
 * 天猫评论存储工具
 */
public class TmallCommentPersister implements Persister<CommentLog, TMallCommentEntity> {

    @Override
    public void persist(ParserEntity<CommentLog> parserEntity, TMallCommentEntity jdCommentEntity) throws PersisterException {
        try {
            PersisterDao.saveComments(parserEntity.getData(),CrawlerType.TMALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
