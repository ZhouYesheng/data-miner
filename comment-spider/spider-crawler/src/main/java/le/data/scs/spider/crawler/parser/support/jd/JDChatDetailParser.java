package le.data.scs.spider.crawler.parser.support.jd;

import le.data.scs.common.entity.meta.chat.ChatDetail;
import le.data.scs.common.entity.meta.chat.ChatLog;
import le.data.scs.common.entity.meta.chat.ChatRole;
import le.data.scs.common.entity.meta.jd.JDSeedEntity;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserException;
import le.data.scs.spider.utils.JsonUtils;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/3/2.
 */
public class JDChatDetailParser implements Parser<List<ChatLog>, JDSeedEntity> {

    private static final String filter_text_pattern = "[^\u4e00-\u9fa5，。~`?？><！\\]\\[\\{\\}、 ……,#$%^&\\-_/\\\\(\\)*!@.!a-zA-Z\\d]+";

    @Override
    public ParserEntity<List<ChatLog>> parser(CrawlerEntity<JDSeedEntity> crawlerEntity) throws ParserException {
        String json = crawlerEntity.getBody();
        List<ChatLog> chatLogs = new ArrayList<ChatLog>();
        ParserEntity<List<ChatLog>> parserEntity = new ParserEntity<List<ChatLog>>();
        try {
            Map<String, Object> jsonMap = JsonUtils.fromJson(json, Map.class);
            if (!jsonMap.containsKey("chatLogList"))
                return null;
            ChatLog log = null;
            List<Map<String, Object>> chatLogList = (List<Map<String, Object>>) jsonMap.get("chatLogList");
            List<Map<String, Object>> chatDetail = null;
            if (!CollectionUtils.isEmpty(chatLogList)) {
                for (Map<String, Object> chatLogMessageLists :
                        chatLogList) {
                    chatDetail = (List<Map<String, Object>>) chatLogMessageLists.get("chatLogMessageList");
                    log = new ChatLog();
                    String watier = null;
                    String customer = null;
                    String type = null;
                    String text = null;
                    String createTime = null;
                    for (Map<String, Object> map : chatDetail) {
                        if (watier == null) {
                            watier = map.get("waiter").toString();
                        }
                        if (customer == null) {
                            customer = map.get("customer").toString();
                        }
                        type = map.get("waiterSend").toString();
                        text = map.get("content").toString();
//                        System.out.println("text="+text);
                        text = text.replaceAll(filter_text_pattern,"");
//                        System.out.println("textfilter="+text);
                        createTime = map.get("created").toString();
                        log.setCustomer(customer);
                        log.setWaiter(watier);
                        if (type.equals("1")) {
                            log.addChatDetail(new ChatDetail(text, createTime, ChatRole.WATIER.toString()));
                        } else {
                            log.addChatDetail(new ChatDetail(text, createTime, ChatRole.CUSTOMER.toString()));
                        }
                    }
                    if (watier != null)
                        chatLogs.add(log);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        parserEntity.setData(chatLogs);
        return parserEntity;
    }
    public static void main(String[] args){
       System.out.println("请问还有什么可以帮您的吗，我尽力帮您解决的哦。有怠慢的地方请您多多包()\\/涵哟~#E-s21#E-s21".replaceAll(filter_text_pattern,""));
    }
}
