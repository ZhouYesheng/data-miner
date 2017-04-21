package le.data.scs.spider.crawler.cookie.support;

import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.distribution.storage.DistributeStorage;
import le.data.scs.spider.distribution.storage.StorageException;
import le.data.scs.spider.distribution.storage.support.ZKStorage;
import le.data.scs.spider.http.cookie.CookieException;
import le.data.scs.spider.crawler.cookie.CookieStorage;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class ZkCookieStorage extends AbstractCookieStorage implements CookieStorage {

    private static final DistributeStorage<String> storage = new ZKStorage<String>();

    private void initCookie() throws CookieException {

    }

    @Override
    public String getCookie(CrawlerType type) throws CookieException {
        try {
            return getCookie(ConfigFactory.getLogin(type.toString()), true);
        } catch (Exception e) {
            throw new CookieException("get Cookie error",e);
        }
    }

    @Override
    public void removeCookie(CrawlerType type) throws CookieException {
        try {
            removeCookie(ConfigFactory.getLogin(type.toString()));
        } catch (Exception e) {
            throw new CookieException("remove Cookie error",e);
        }
    }

    @Override
    public String readCookie(String storePath) throws CookieException {
        try {
            return storage.get(storePath,String.class);
        } catch (StorageException e) {
            throw new CookieException("read cookie error", e);
        }
    }

    @Override
    public void writeBack(String storePath, String cookieJson) throws CookieException {
        try {
            storage.store(storePath,cookieJson,true);
        } catch (StorageException e) {
            throw new CookieException("write cookie error", e);
        }
    }

    @Override
    public void delCookie(String storePath) throws CookieException {
        try {
            storage.del(storePath);
        } catch (StorageException e) {
            throw new CookieException("del cookie from "+storePath+" error",e);
        }
    }
}
