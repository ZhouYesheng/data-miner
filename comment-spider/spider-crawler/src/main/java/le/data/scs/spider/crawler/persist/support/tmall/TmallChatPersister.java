package le.data.scs.spider.crawler.persist.support.tmall;

import le.data.scs.common.entity.meta.chat.ChatLog;
import le.data.scs.common.entity.meta.tmall.TMallSeedEntity;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterException;
import le.data.scs.spider.crawler.persist.support.mysql.PersisterDao;
import le.data.scs.spider.utils.JsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public class TmallChatPersister implements Persister<ChatLog, TMallSeedEntity> {

    @Override
    public void persist(ParserEntity<ChatLog> parserEntity, TMallSeedEntity tMallSeedEntity) throws PersisterException {
        try {
            List<ChatLog> chatLogList = new ArrayList<ChatLog>();
            chatLogList.add(parserEntity.getData());
            PersisterDao.saveChatLogs(chatLogList,CrawlerType.TMALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
