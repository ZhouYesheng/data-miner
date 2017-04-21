package le.data.scs.spider.crawler.persist;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class PersisterException extends Exception {

    public PersisterException(String message) {
        super(message);
    }

    public PersisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
