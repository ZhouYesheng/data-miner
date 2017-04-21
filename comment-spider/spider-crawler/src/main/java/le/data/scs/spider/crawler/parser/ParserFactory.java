package le.data.scs.spider.crawler.parser;

import le.data.scs.spider.config.BaseFactory;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class ParserFactory {
    public synchronized static <Data,Meta> Parser<Data,Meta> getParser(String classname) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return BaseFactory.getInstance(classname);
    }

    public synchronized static final <Data,Meta> Parser<Data,Meta> getParser(Class clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return BaseFactory.getInstance(clazz);
    }
}
