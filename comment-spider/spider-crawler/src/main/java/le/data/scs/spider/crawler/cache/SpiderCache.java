package le.data.scs.spider.crawler.cache;

import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.distribution.cache.CacheException;
import le.data.scs.spider.distribution.cache.DistributedCache;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.utils.ClassUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/3/1.
 * 缓存工厂类，通过该类获取系统需要的缓存
 */
public class SpiderCache {

    private static final Map<CrawlerType, DistributedCache<String>> cachePool = new Hashtable<CrawlerType, DistributedCache<String>>();

    public synchronized static DistributedCache<String> getCache(CrawlerType type) throws CacheException {
        if (cachePool.containsKey(type))
            return cachePool.get(type);
        DistributedCache<String> cache = null;
        if (cache == null) {
            String instance = null;
            String cachePath = null;
            try {
                instance = ConfigFactory.getProperty("spider.crawler.cache.classname");
                cachePath = ConfigFactory.getProperty("spider.crawler.cache.name")+"/"+type.toString();
                cache = ClassUtils.newInstance(instance, new String[]{cachePath}, new Class[]{String.class});
                cachePool.put(type, cache);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CacheException("get cache error cache instance =[" + instance + "] cache path is -[" + cachePath + "]");
            }
        }
        return cache;
    }

}
