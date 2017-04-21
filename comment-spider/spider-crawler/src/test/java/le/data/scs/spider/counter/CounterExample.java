package le.data.scs.spider.counter;

import le.data.scs.spider.distribution.counter.CounterException;
import le.data.scs.spider.crawler.counter.CounterFactory;

/**
 * Created by young.yang on 2017/2/25.
 */
public class CounterExample {
    public static void main(String[] args) throws CounterException {
        String counter = "/spider/counter/crawler/jd/request";
        CounterFactory.getCounter(counter).set(1);
        CounterFactory.getCounter(counter).increment(10);
        System.out.println(CounterFactory.getCounter(counter).get());
    }
}
