package le.data.scs.spider.distribution.storage;

/**
 * Created by young.yang on 2017/2/25.
 * 分布式共享存储
 */
public interface DistributeStorage<T> {
    /**
     * 存储数据
     * @param path
     * @param t
     * @param create
     * @throws StorageException
     */
    public void store(String path, T t, boolean create) throws StorageException;

    /**
     * 获取数据
     * @param path
     * @param tClass
     * @return
     * @throws StorageException
     */
    public T get(String path, Class<T> tClass) throws StorageException;

    /**
     * 删除数据
     * @param path
     * @throws StorageException
     */
    public void del(String path) throws StorageException;

    /**
     * 是否存在数据
     * @param path
     * @return
     * @throws StorageException
     */
    public boolean exist(String path) throws StorageException;
}
