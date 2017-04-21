package le.data.scs.spider.storage;

import le.data.scs.spider.distribution.storage.DistributeStorage;
import le.data.scs.spider.distribution.storage.StorageException;
import le.data.scs.spider.distribution.storage.support.ZKStorage;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public class StorageTest {
    public static void main(String[] args) throws StorageException {
        DistributeStorage storage = new ZKStorage<String>();
        String path = "/yangyong";
        storage.store(path,"1111",true);
        System.out.println(storage.get(path,String.class));
        storage.del(path);
        System.out.println(storage.get(path,String.class));
    }
}
