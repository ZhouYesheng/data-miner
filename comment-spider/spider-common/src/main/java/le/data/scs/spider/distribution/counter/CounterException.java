package le.data.scs.spider.distribution.counter;

/**
 * Created by young.yang on 2017/2/25.
 */
public class CounterException extends Exception {
    public CounterException(String message) {
        super(message);
    }

    public CounterException(String message, Throwable cause) {
        super(message, cause);
    }
}
