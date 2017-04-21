package le.data.scs.spider.crawler.crawler.support.tmall;

import le.data.scs.common.entity.meta.tmall.TMallNickEntity;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.http.cookie.CookieException;
import le.data.scs.spider.crawler.cookie.SpiderCookieFactory;
import le.data.scs.spider.crawler.crawler.common.CrawlerAdapter;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TmallNickNamesCrawler extends CrawlerAdapter<TMallNickEntity> {

    public TmallNickNamesCrawler(CrawlerType type) {
        super(type);
    }

    public TmallNickNamesCrawler() {
        this(CrawlerType.TMALL);
    }

    @Override
    public Request createRuquest(TMallNickEntity s) throws CrawlerException {
        String cookie = findCookie(this.type);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Cookie", cookie);
        return new Request(s.getNickUrl(), header,null,"gb2312");
    }

    @Override
    public boolean check(Request request, Response response, TMallNickEntity s) throws CrawlerException {
        if (response.getStatusCode() != 200)
            return false;
        Document document = Jsoup.parse(response.getContent());
        Element element = document.getElementById("employeeNick");
        if (element == null) {
            try {
                SpiderCookieFactory.delCookie(this.type);
            } catch (CookieException e) {
                throw new CrawlerException("delete expired Cookie error", new CookieException("cookie expired"));
            }
            throw new CrawlerException("Cookie expire", new CookieException("cookie expired"));
        }
        return true;
    }
}
