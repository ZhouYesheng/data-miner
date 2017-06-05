package le.data.scs.spider.distribution.task.leadertask;

import le.data.scs.spider.distribution.task.DistributedTaskException;

/**
 * Created by young.yang on 2017/2/25.
 * 分布式LeaderTask，该任务的特点是，多个实例同时只能有一个实例工作，其他的作为备份
 */
public interface LeaderTask<T> {
    public T runTask(boolean is_stay) throws DistributedTaskException;
}
