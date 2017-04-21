package le.data.scs.spider.cookie;

import le.data.scs.spider.http.cookie.CookieException;
import le.data.scs.spider.crawler.cookie.SpiderCookieFactory;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TMallCookieExample {
    public static void main(String[] args) throws CookieException {
        String cookie = SpiderCookieFactory.getCookie(CrawlerType.TMALL);

    }
}
