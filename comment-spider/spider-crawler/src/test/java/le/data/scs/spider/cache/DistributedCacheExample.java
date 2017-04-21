package le.data.scs.spider.cache;

import le.data.scs.spider.distribution.cache.CacheException;
import le.data.scs.spider.distribution.cache.DistributedCache;
import le.data.scs.spider.distribution.cache.support.ZKDistributedCache;

/**
 * Created by yangyong3 on 2017/3/1.
 */
public class DistributedCacheExample {
    public static void main(String[] args) throws CacheException {
        String path = "/spider/queue/crawler/SpiderCache";
        DistributedCache<String> cache = new ZKDistributedCache<String>(path);
        for(int i=0;i<10;i++){
            cache.put("yangyong_"+i,"yangyong_"+i);
        }
        System.out.println(cache.get("yangyong_5",String.class));
    }
}
