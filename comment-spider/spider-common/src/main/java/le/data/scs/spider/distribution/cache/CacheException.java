package le.data.scs.spider.distribution.cache;

/**
 * Created by yangyong3 on 2017/3/1.
 */
public class CacheException extends Exception {

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }
}
