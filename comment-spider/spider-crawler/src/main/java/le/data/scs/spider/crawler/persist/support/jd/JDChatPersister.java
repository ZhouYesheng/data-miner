package le.data.scs.spider.crawler.persist.support.jd;

import le.data.scs.common.entity.meta.chat.ChatLog;
import le.data.scs.common.entity.meta.jd.JDSeedEntity;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterException;
import le.data.scs.spider.crawler.persist.support.mysql.PersisterDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class JDChatPersister implements Persister<List<ChatLog>, JDSeedEntity> {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");

    @Override
    public void persist(ParserEntity<List<ChatLog>> parserEntity, JDSeedEntity jdSeedEntity) throws PersisterException {
        try {
            PersisterDao.saveChatLogs(parserEntity.getData(), CrawlerType.JD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
