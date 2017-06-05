package le.data.scs.spider.crawler.parser;

import le.data.scs.spider.config.BaseFactory;

/**
 * Created by yangyong3 on 2017/2/23.
 * 解析器工厂类
 */
public class ParserFactory {
    /**
     * 获取相应的解析器
     * @param classname
     * @param <Data>
     * @param <Meta>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public synchronized static <Data,Meta> Parser<Data,Meta> getParser(String classname) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return BaseFactory.getInstance(classname);
    }

    /**
     * 获取一个解析器
     * @param clazz
     * @param <Data>
     * @param <Meta>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public synchronized static final <Data,Meta> Parser<Data,Meta> getParser(Class clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return BaseFactory.getInstance(clazz);
    }
}
