package le.data.scs.spider.crawler.counter;

import le.data.scs.spider.distribution.counter.Counter;
import le.data.scs.spider.distribution.counter.CounterException;
import le.data.scs.spider.distribution.counter.support.ZKCounter;
import le.data.scs.spider.utils.ClassUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/1/13.
 */
public class CounterFactory {
    private static final Map<String,Counter> counterCache = new Hashtable<String,Counter>();

    public synchronized static final Counter getCounter(String counter) throws CounterException {
        if(counterCache.containsKey(counter))
            return counterCache.get(counter);
        Counter instance = null;
        try {
            instance = ClassUtils.newInstance(ZKCounter.class,new Object[]{counter},new Class[]{String.class});
            counterCache.put(counter,instance);
            return instance;
        } catch (Exception e) {
            throw new CounterException("create a counter "+ZKCounter.class.getName()+",error ",e);
        }
    }
    public static final String print() throws CounterException {
        StringBuilder sb =  new StringBuilder();
        for(Map.Entry<String,Counter> entry:counterCache.entrySet()){
            sb.append(entry.getKey()+"="+entry.getValue().get()+"\n");
        }
        return sb.toString();
    }
}
