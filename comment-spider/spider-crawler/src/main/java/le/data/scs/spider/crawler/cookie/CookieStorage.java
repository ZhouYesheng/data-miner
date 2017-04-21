package le.data.scs.spider.crawler.cookie;

import le.data.scs.spider.http.cookie.CookieException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public interface CookieStorage {

    public String getCookie(CrawlerType type) throws CookieException;

    public void removeCookie(CrawlerType type) throws CookieException;
}
