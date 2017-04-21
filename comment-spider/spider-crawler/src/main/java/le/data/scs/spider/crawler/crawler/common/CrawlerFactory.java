package le.data.scs.spider.crawler.crawler.common;

import le.data.scs.spider.config.BaseFactory;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class CrawlerFactory {

    public synchronized static <Meta> Crawler<Meta> getCrawler(String classname) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return BaseFactory.getInstance(classname);
    }

    public synchronized static <Meta> Crawler<Meta> getCrawler(Class clazz) throws InstantiationException, IllegalAccessException {
        return BaseFactory.getInstance(clazz);
    }
}
