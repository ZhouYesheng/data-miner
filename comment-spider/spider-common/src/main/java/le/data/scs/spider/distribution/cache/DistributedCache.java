package le.data.scs.spider.distribution.cache;

/**
 * Created by yangyong3 on 2017/3/1.
 * 分布式缓存
 */
public interface DistributedCache<T> {

    /**
     * 判断key是否存在
     * @param key
     * @return
     * @throws CacheException
     */
    public boolean exist(String key) throws CacheException;

    /**
     * 保存缓存数据
     * @param key
     * @param value
     * @throws CacheException
     */
    public void put(String key, T value) throws CacheException;

    /**
     * 获取缓存数据
     * @param key
     * @param tClass
     * @return
     * @throws CacheException
     */
    public T get(String key, Class<T> tClass) throws CacheException;

    /**
     * 删除缓存数据
     * @param key
     * @throws CacheException
     */
    public void del(String key) throws CacheException;
}
