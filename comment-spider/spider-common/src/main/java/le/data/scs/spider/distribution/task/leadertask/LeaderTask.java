package le.data.scs.spider.distribution.task.leadertask;

import le.data.scs.spider.distribution.task.DistributedTaskException;

/**
 * Created by young.yang on 2017/2/25.
 */
public interface LeaderTask<T> {
    public T runTask(boolean is_stay) throws DistributedTaskException;
}
