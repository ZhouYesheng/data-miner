package le.data.scs.spider.crawler.cookie;

import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.http.cookie.CookieException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.utils.ClassUtils;

/**
 * Created by yangyong3 on 2017/2/22.
 */
public class SpiderCookieFactory {

    private static CookieStorage storage;

    static {
        try {
            storage = ClassUtils.newInstance(ConfigFactory.getConfig().getSpiderCookie().getStorage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCookie(CrawlerType type) throws CookieException {
        return storage.getCookie(type);
    }

    public static void delCookie(CrawlerType type) throws CookieException{
        storage.removeCookie(type);
    }

}
