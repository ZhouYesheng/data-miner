package com.young.spider.distribution.task.leadertask;


import com.young.spider.distribution.task.DistributedTaskException;

/**
 * Created by young.yang on 2017/2/25.
 */
public interface LeaderTask<T> {
    public T runTask(boolean is_stay) throws DistributedTaskException;
}
