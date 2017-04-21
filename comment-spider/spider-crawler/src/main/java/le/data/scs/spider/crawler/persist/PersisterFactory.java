package le.data.scs.spider.crawler.persist;

import le.data.scs.spider.config.BaseFactory;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class PersisterFactory {

    public synchronized static <Data,Meta> Persister<Data,Meta> getPersister(String classname) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return BaseFactory.getInstance(classname);
    }

    public synchronized static final <Data,Meta> Persister<Data,Meta> getPersister(Class clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return BaseFactory.getInstance(clazz);
    }
}
