package le.data.scs.spider.distribution.counter;

/**
 * Created by young.yang on 2017/2/25.
 * 分布式计数器
 */
public interface Counter {
    /**
     * 设置初始值
     * @param value
     * @throws CounterException
     */
    public void set(long value) throws CounterException;
    /**
     * 递增
     * @param increment
     * @throws CounterException
     */
    public void increment(long increment) throws CounterException;
    /**
     * 获取计数器的值
     * @return
     * @throws CounterException
     */
    public long get() throws CounterException;
}
