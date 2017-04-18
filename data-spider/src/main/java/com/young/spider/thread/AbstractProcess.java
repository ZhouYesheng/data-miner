package com.young.spider.thread;


import com.young.spider.config.ConfigFactory;
import com.young.spider.distribution.mq.MQFactory;
import com.young.spider.distribution.mq.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by young.yang on 2017/2/25.
 */
public abstract class AbstractProcess<T> implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(AbstractProcess.class);

    private String seedQueueName;

    private String errorQueueName;

    protected MessageQueue<T> seedQueue;

    protected MessageQueue<T> errorQueue;

    private Class<T> tClass;

    protected boolean stop = false;

    private static final int retryNum = 3;

    public AbstractProcess(String seedQueueName, String errorQueueName, Class<T> tClass) {
        this.seedQueueName = seedQueueName;
        this.errorQueueName = errorQueueName;
        this.tClass = tClass;
    }

    public void stop() {
        this.stop = true;
    }

    public abstract void process(T t) throws Exception;

    public void run() {
        while (!stop) {
            try {
                if (seedQueue == null) {
                    seedQueue = MQFactory.getMessageQueue(seedQueueName);
                }
                if (errorQueue == null) {
                    errorQueue = MQFactory.getMessageQueue(errorQueueName);
                }
                T t = seedQueue.take(tClass);
                int count = 0;
                while (count < retryNum) {
                    try {
                        if (t == null)
                            break;
                        process(t);
                        log.info("crawler seed from " + seedQueueName + " [" + t + "] ok");
                        break;
                    } catch (Exception e) {
                        count++;
                        log.error("crawler seed [" + t + "] error retry num is [" + count + "]");
                        e.printStackTrace();
                        ConfigFactory.sleepTime();
                        if (count == retryNum) {
                            errorQueue.offer(t);
                            log.info("crawler " + t + " fail " + count + " times so put seed to " + errorQueueName + " [" + t + "] ok");
                        }
                    }
                }
                ConfigFactory.sleepTime();
            } catch (Exception e) {
                e.printStackTrace();
                ConfigFactory.sleepTime();
            }
        }
        log.info(Thread.currentThread().getName() + " thread execute over,exit ");
    }
}
