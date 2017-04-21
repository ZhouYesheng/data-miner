package le.data.scs.spider.crawler.task;

import le.data.scs.common.entity.meta.jd.JDCommentEntity;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.persist.support.mysql.PersisterDao;
import le.data.scs.spider.distribution.task.leadertask.ZKDistributedLeaderTaskAdapter;
import le.data.scs.spider.crawler.service.JDSpiderService;
import le.data.scs.spider.utils.JsonUtils;
import le.data.scs.spider.utils.MD5;

import java.util.List;

/**
 * Created by yangyong3 on 2017/3/8.
 */
public class JDCommentSeedTask extends ZKDistributedLeaderTaskAdapter {


    private JDSpiderService jdSpiderService = new JDSpiderService();

    @Override
    protected Object task() throws Exception {
        List<String> commentEntitys = PersisterDao.listSeedByType(CrawlerType.JD.toString());
        JDCommentEntity commentEntity = null;
        for(String line:commentEntitys){
            commentEntity = JsonUtils.fromJson(line,JDCommentEntity.class);
            jdSpiderService.jdCommentSeedService(commentEntity);
        }
        return null;
    }



    @Override
    protected Object change() {
        return null;
    }
    public static void main(String[] args) throws Exception {
        JDCommentSeedTask task = new JDCommentSeedTask();
        task.task();
    }
}
