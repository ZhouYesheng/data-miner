package le.data.scs.spider.crawler.crawler.support.tmall;

import le.data.scs.common.entity.meta.tmall.TMallSeedEntity;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.http.cookie.CookieException;
import le.data.scs.spider.crawler.cookie.SpiderCookieFactory;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.utils.JsonUtils;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public class TmallChatDetailCrawler extends TmallCrawlerAdapter<TMallSeedEntity> {
    private static final String baseUrl = "https://zizhanghao.taobao.com/subaccount/monitor/chatRecordHtml.htm?action=/subaccount/monitor/ChatRecordQueryAction&eventSubmitDoQueryChatContent=anything";

    private static final String params = "chatContentQuery";

    @Override
    public Request createRuquest(TMallSeedEntity tMallSeedEntity) throws CrawlerException {
        try {
            return baseReqeust(baseUrl,params, URLEncoder.encode(JsonUtils.toJson(tMallSeedEntity.getChatContentQuery()),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean check(Request request, Response response, TMallSeedEntity tMallSeedEntity) throws CrawlerException {
        if (response.getContent().contains("登录页面")) {
            try {
                SpiderCookieFactory.delCookie(this.type);
            } catch (CookieException e) {
                throw new CrawlerException("delete cookie error", e);
            }
            throw new CrawlerException("Cookie expire", new CookieException("cookie expired"));
        }
        return true;
    }
}
