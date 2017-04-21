package le.data.scs.spider.distribution.cache.support;

import le.data.scs.spider.distribution.cache.CacheException;
import le.data.scs.spider.distribution.cache.DistributedCache;
import le.data.scs.spider.distribution.storage.DistributeStorage;
import le.data.scs.spider.distribution.storage.StorageException;
import le.data.scs.spider.distribution.storage.support.ZKStorage;

/**
 * Created by yangyong3 on 2017/3/1.
 */
public class ZKDistributedCache<T> implements DistributedCache<T> {

    private DistributeStorage<T> storage = new ZKStorage<T>();

    private String cachePath;

    public ZKDistributedCache(String cachePath) {
        this.cachePath = cachePath;
    }

    @Override
    public boolean exist(String key) throws CacheException {
        String zkPth = cachePath + "/" + key;
        try {
            return storage.exist(zkPth);
        } catch (StorageException e) {
            throw new CacheException("SpiderCache exist error key = " + zkPth, e);
        }
    }

    @Override
    public void put(String key, T value) throws CacheException {
        String zkPth = cachePath + "/" + key;
        try {
            storage.store(zkPth, value, true);
        } catch (StorageException e) {
            throw new CacheException("put key error key = " + zkPth + ",value =" + value, e);
        }
    }

    @Override
    public T get(String key,Class<T> tClass) throws CacheException {
        String zkPth = cachePath + "/" + key;
        try {
            return storage.get(zkPth,tClass);
        } catch (StorageException e) {
            throw new CacheException("get key error key = " + zkPth, e);
        }
    }

    @Override
    public void del(String key) throws CacheException {
        String zkPth = cachePath + "/" + key;
        try {
            storage.del(zkPth);
        } catch (StorageException e) {
            throw new CacheException("destory error path = " + cachePath, e);
        }
    }
}
