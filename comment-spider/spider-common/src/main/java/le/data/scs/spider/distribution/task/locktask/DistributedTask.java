package le.data.scs.spider.distribution.task.locktask;

import le.data.scs.spider.distribution.task.DistributedTaskException;

/**
 * Created by young.yang on 2017/2/25.
 * 分布式抢占锁任务，多个实例共同抢占一个资源，只有获取锁的实例可以进行操作
 */
public interface DistributedTask<T> {
    public T runTask() throws DistributedTaskException;
}
