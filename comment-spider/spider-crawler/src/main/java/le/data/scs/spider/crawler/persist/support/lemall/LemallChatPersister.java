package le.data.scs.spider.crawler.persist.support.lemall;

import le.data.scs.common.entity.meta.chat.ChatLog;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterException;
import le.data.scs.spider.crawler.persist.support.mysql.PersisterDao;

import java.util.List;

/**
 * Created by yangyong3 on 2017/3/24.
 */
public class LemallChatPersister implements Persister<List<ChatLog>, String> {
    @Override
    public void persist(ParserEntity<List<ChatLog>> parserEntity, String s) throws PersisterException {
        try {
            PersisterDao.saveChatLogs(parserEntity.getData(), CrawlerType.LEMALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
