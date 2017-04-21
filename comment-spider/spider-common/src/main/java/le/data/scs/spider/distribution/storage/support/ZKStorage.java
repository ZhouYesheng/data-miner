package le.data.scs.spider.distribution.storage.support;

import le.data.scs.spider.distribution.storage.DistributeStorage;
import le.data.scs.spider.distribution.storage.StorageException;
import le.data.scs.spider.serialization.Serialization;
import le.data.scs.spider.serialization.support.JsonSerialization;
import le.data.scs.spider.zkclient.ZKClientFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * Created by young.yang on 2017/2/25.
 */
public class ZKStorage<T> implements DistributeStorage<T> {

    private CuratorFramework zkClient;

    private static final String encode = "utf-8";

    private final Serialization<T, String> jsonSerialization = new JsonSerialization<T>();

    public ZKStorage() {
        this.zkClient = ZKClientFactory.getZKClient();
    }

    @Override
    public void store(String path, T t, boolean create) throws StorageException {
        try {
            if (zkClient.checkExists().forPath(path) == null) {
                if (create) {
//                    zkClient.create().creatingParentsIfNeeded();
//                    zkClient.create().forPath(path);
                    zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
                } else
                    throw new StorageException("path is not exist " + path);
            }
            zkClient.setData().forPath(path, jsonSerialization.serialization(t).getBytes(encode));
        } catch (Exception e) {
            throw new StorageException("store " + t + " to path " + path + " error", e);
        }
    }

    @Override
    public T get(String path, Class<T> tClass) throws StorageException {
        try {
            if (zkClient.checkExists().forPath(path) == null)
                return null;
            byte[] bytes = zkClient.getData().forPath(path);
            if (bytes == null)
                return null;
            return jsonSerialization.unserialization(new String(bytes, encode), tClass);
        } catch (Exception e) {
            throw new StorageException("get data from path " + path + " error", e);
        }
    }

    @Override
    public void del(String path) throws StorageException {
        try {
            if (zkClient.checkExists().forPath(path) == null)
                return;
            zkClient.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            throw new StorageException("delete path error " + path);
        }
    }

    @Override
    public boolean exist(String path) throws StorageException {
        try {
            return zkClient.checkExists().forPath(path) != null;
        } catch (Exception e) {
            throw new StorageException("exist error " + path);
        }
    }
}
