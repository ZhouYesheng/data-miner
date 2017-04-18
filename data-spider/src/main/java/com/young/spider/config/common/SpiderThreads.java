package com.young.spider.config.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangyong3 on 2017/3/1.
 */
@XStreamAlias("threads")
public class SpiderThreads implements Serializable{

    @XStreamImplicit(itemFieldName = "thread")
    private List<SpiderThread> threads;

    @XStreamAsAttribute
    private int poolsize;

    public List<SpiderThread> getThreads() {
        return threads;
    }

    public void setThreads(List<SpiderThread> threads) {
        this.threads = threads;
    }

    public int getPoolsize() {
        return poolsize;
    }

    public void setPoolsize(int poolsize) {
        this.poolsize = poolsize;
    }
}
