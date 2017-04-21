package le.data.scs.spider.distribution.cache;

/**
 * Created by yangyong3 on 2017/3/1.
 */
public interface DistributedCache<T> {

    public boolean exist(String key) throws CacheException;

    public void put(String key, T value) throws CacheException;

    public T get(String key, Class<T> tClass) throws CacheException;

    public void del(String key) throws CacheException;
}
