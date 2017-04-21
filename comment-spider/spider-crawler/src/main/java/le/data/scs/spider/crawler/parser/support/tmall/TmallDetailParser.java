package le.data.scs.spider.crawler.parser.support.tmall;

import le.data.scs.common.entity.meta.chat.ChatDetail;
import le.data.scs.common.entity.meta.chat.ChatLog;
import le.data.scs.common.entity.meta.chat.ChatRole;
import le.data.scs.common.entity.meta.tmall.TMallSeedEntity;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserException;
import le.data.scs.spider.utils.JsoupUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public class TmallDetailParser extends TmallBaseParser implements Parser<ChatLog, TMallSeedEntity> {
    @Override
    public ParserEntity<ChatLog> parser(CrawlerEntity<TMallSeedEntity> crawlerEntity) throws ParserException {
        //解析html
        ParserEntity<ChatLog> parserEntity = new ParserEntity<ChatLog>();
        String html = crawlerEntity.getBody();
        ChatLog log = process(html, crawlerEntity.getMeta());
        if (log != null)
            parserEntity.setData(log);
        return parserEntity;
    }

    private ChatLog process(String html, TMallSeedEntity customerEntity) {
        ChatLog log = new ChatLog();
        Document document = Jsoup.parse(html);
        Element div = JsoupUtils.selectFirstElement(document, "div.chatlog-list");
        if (div == null)
            return null;
        Elements elements = JsoupUtils.selectElements(div,"p");
        Element element = null;
        Element time = null;
        time = elements.first();
        String nickName = null;
        String[] temp = null;
        String date = time.text().trim();
        String timeString = null;
        String waiter = customerEntity.getChatContentQuery().getEmployeeUserNick()[0];
        String customer = customerEntity.getChatContentQuery().getCustomerUserNick()[0];
        ChatDetail detail = null;
        log.setCustomer(customer);
        log.setWaiter(waiter);
        for (int i = 1; i < elements.size(); i++) {
            element = elements.get(i);
            if (element.attr("class").equals("me")) {
                detail = new ChatDetail();
                temp = element.text().split("\\(");
                nickName = "cntaobao"+temp[0].trim();
                timeString = temp[1].replaceAll("\\)", "");
            } else {
                detail.setText(detail.getText() + " " + element.text());
                detail.setCreateTime(date + " " + timeString);
                if(customer.equals(nickName)){
                    detail.setRole(ChatRole.CUSTOMER.toString());
                }else{
                    detail.setRole(ChatRole.WATIER.toString());
                }
                log.addChatDetail(detail);
            }
        }
        return log;
    }
}
