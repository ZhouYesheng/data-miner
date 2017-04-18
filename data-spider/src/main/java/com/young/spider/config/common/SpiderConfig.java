package com.young.spider.config.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangyong3 on 2017/2/16.
 */
@XStreamAlias("spider")
public class SpiderConfig implements Serializable {

    private List<SpiderProperty> properties;

    private SpiderMessageQueue messageQueue;

    @XStreamAlias("cookies")
    private SpiderCookie spiderCookie;

    private SpiderThreads threads;

    private List<SpiderTask> tasks;

    public List<SpiderTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<SpiderTask> tasks) {
        this.tasks = tasks;
    }

    public List<SpiderProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<SpiderProperty> properties) {
        this.properties = properties;
    }

    public SpiderMessageQueue getMessageQueue() {
        return messageQueue;
    }

    public void setMessageQueue(SpiderMessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public SpiderCookie getSpiderCookie() {
        return spiderCookie;
    }

    public void setSpiderCookie(SpiderCookie spiderCookie) {
        this.spiderCookie = spiderCookie;
    }

    public SpiderThreads getThreads() {
        return threads;
    }

    public void setThreads(SpiderThreads threads) {
        this.threads = threads;
    }
}
