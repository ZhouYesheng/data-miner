package le.data.scs.spider.crawler.task;

import le.data.scs.common.entity.meta.jd.JDCommentEntity;
import le.data.scs.common.entity.meta.tmall.TMallCommentEntity;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.persist.support.mysql.PersisterDao;
import le.data.scs.spider.distribution.task.leadertask.ZKDistributedLeaderTaskAdapter;
import le.data.scs.spider.crawler.service.TMallSpiderService;
import le.data.scs.spider.utils.JsonUtils;
import le.data.scs.spider.utils.MD5;

import java.util.List;

/**
 * Created by yangyong3 on 2017/3/9.
 * 天猫评论种子发现任务
 */
public class TmallCommentSeedTask extends ZKDistributedLeaderTaskAdapter {

    private TMallSpiderService tMallSpiderService = new TMallSpiderService();

    @Override
    protected Object task() throws Exception {
        List<String> commentEntitys = PersisterDao.listSeedByType(CrawlerType.TMALL.toString());
        TMallCommentEntity commentEntity = null;
        for(String line:commentEntitys){
            commentEntity = JsonUtils.fromJson(line,TMallCommentEntity.class);
            tMallSpiderService.tmallCommentSeedService(commentEntity);
        }
        return null;
    }


    @Override
    protected Object change() {
        return null;
    }

    public static void main(String[] args) throws Exception {
        TmallCommentSeedTask task = new TmallCommentSeedTask();
        task.task();
    }
}
