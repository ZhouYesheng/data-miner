package le.data.scs.spider.crawler.task;

import le.data.scs.common.entity.meta.chat.ChatDetail;
import le.data.scs.common.entity.meta.chat.ChatLog;
import le.data.scs.common.entity.meta.chat.ChatRole;
import le.data.scs.common.jdbc.DBConnection;
import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.crawler.cache.SpiderCache;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.entity.common.SpiderStatus;
import le.data.scs.spider.crawler.persist.Persister;
import le.data.scs.spider.crawler.persist.PersisterFactory;
import le.data.scs.spider.crawler.persist.support.lemall.LemallChatPersister;
import le.data.scs.spider.distribution.task.leadertask.ZKDistributedLeaderTaskAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyong3 on 2017/3/24.
 */
public class LeMallWebChatTask extends ZKDistributedLeaderTaskAdapter {

    private static final Logger log = LoggerFactory.getLogger(LeMallWebChatTask.class);

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static final long one_hour = 1000 * 60 * 60;

    private List<ChatLog> fetchChatLogs(long start, long end) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ChatLog> chatLogs = new ArrayList<ChatLog>();
        ChatLog chatLog = null;
        try {
            StringBuilder sql = new StringBuilder(100);
            sql.append("SELECT a.id,IF(LENGTH(a.chat_user_id)<10,a.chat_user_id,b.customer_guid) AS chat_user_id,a.chat_session_id,a.post_time,a.message_text FROM chat_message a LEFT JOIN customer b ON a.chat_user_id = b.customer_guid  \n" +
                    "WHERE  a.post_time >=?  AND a.post_time < ? ORDER BY a.chat_session_id,a.post_time");
            log.info("sql--[" + sql.toString() + "]");
            con = DBConnection.getConnection(ConfigFactory.getProperty("spider.crawler.lemall.mysql.url"), ConfigFactory.getProperty("spider.crawler.lemall.mysql.user"), ConfigFactory.getProperty("spider.crawler.lemall.mysql.pass"));
            stmt = con.prepareStatement(sql.toString());
            stmt.setLong(1, start);
            stmt.setLong(2, end);
            rs = stmt.executeQuery();
            String chat_user_id = null;
            Long time = null;
            long old_session = 0l;
            long current_session = 0l;
            while (rs.next()) {
                current_session = rs.getLong("chat_session_id");
                chat_user_id = rs.getString("chat_user_id");
                time = rs.getLong("post_time");
                if (old_session != current_session) {
                    if (chatLog != null) {
                        chatLogs.add(chatLog);
                    }
                    old_session = current_session;
                    chatLog = new ChatLog();
                    chatLog.setId(current_session + "");
                    chatLog.setDataTime(format.format(new Date(time)));
                }
                ChatDetail detail = new ChatDetail();
                detail.setCreateTime(format.format(new Date(time)));
                detail.setText(rs.getString("message_text"));
                if (chat_user_id.length() == 6) {
                    chatLog.setWaiter(chat_user_id);
                    detail.setRole(ChatRole.WATIER.toString());
                } else {
                    chatLog.setCustomer(chat_user_id);
                    detail.setRole(ChatRole.CUSTOMER.toString());
                }
                chatLog.addChatDetail(detail);
            }
            if (chatLog != null)
                chatLogs.add(chatLog);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return chatLogs;
    }

    @Override
    protected Object task() throws Exception {
        String data_time = dataFormat.format(new Date(System.currentTimeMillis() - (one_hour * 24)));
        String before_data_time = dataFormat.format(new Date(System.currentTimeMillis() - (one_hour * 24 * 2)));
        SpiderCache.getCache(CrawlerType.LEMALL).del(CrawlerType.LEMALL.toString() + "_" + before_data_time);
        crawler(data_time);
        return null;
    }

    private Object crawler(String data_time) throws Exception {
        String cache_key = CrawlerType.LEMALL.toString() + "_" + data_time;
        String result = null;
        if (SpiderCache.getCache(CrawlerType.LEMALL).exist(cache_key)) {
            result = SpiderCache.getCache(CrawlerType.LEMALL).get(cache_key, String.class);
            if (SpiderStatus.FINISHED.toString().equals(result)) {
                log.info(cache_key + " push seed finished " + new Date());
                ConfigFactory.sleepTime();
                return null;
            }
        } else {
            SpiderCache.getCache(CrawlerType.LEMALL).put(cache_key, SpiderStatus.RUNNING.toString());
        }
        long start = dataFormat.parse(data_time).getTime();
        int batch_time = Integer.parseInt(ConfigFactory.getProperty("spider.crawler.lemall.batch.size"));
        int interval = 24 / batch_time;
        List<ChatLog> chatLogs = null;
        Persister<List<ChatLog>, String> persister = PersisterFactory.getPersister(LemallChatPersister.class);
        ParserEntity<List<ChatLog>> parserEntity = null;
        String cacheSubkey = null;
        for (int i = 0; i < batch_time; i++) {
            long startlong = start + (i * interval * one_hour);
            long endlong = start + (i + 1) * interval * one_hour;
            cacheSubkey = cache_key + "/" + startlong + "_" + endlong;
            if (!SpiderCache.getCache(CrawlerType.LEMALL).exist(cacheSubkey)) {
                chatLogs = fetchChatLogs(startlong, endlong);
                parserEntity = new ParserEntity<List<ChatLog>>();
                parserEntity.setData(chatLogs);
                persister.persist(parserEntity, null);
                SpiderCache.getCache(CrawlerType.LEMALL).put(cacheSubkey, "finished");
            }
        }
        SpiderCache.getCache(CrawlerType.LEMALL).put(cache_key, SpiderStatus.FINISHED.toString());
        return null;
    }

    @Override
    protected Object change() {
        return null;
    }

    public static void main(String[] args) throws Exception {
        LeMallWebChatTask task = new LeMallWebChatTask();
//        task.runTask(true);
        if (args.length != 1) {
            System.out.println("this program must input 1 param,eg:2017-04-05");
            System.exit(-1);
        }
        task.crawler(args[0]);
    }
}
