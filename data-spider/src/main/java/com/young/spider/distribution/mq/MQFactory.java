package com.young.spider.distribution.mq;



import com.young.spider.config.ConfigFactory;
import com.young.spider.config.support.SpiderConfigException;
import com.young.spider.utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class MQFactory {

    private static final Map<String, MessageQueue> queueCache = new Hashtable<String, MessageQueue>();

    public synchronized static <T> MessageQueue<T> getMessageQueue(String queueName) throws SpiderConfigException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (queueCache.containsKey(queueName))
            return queueCache.get(queueName);
        String classname = ConfigFactory.getConfig().getMessageQueue().getClassname();
        Class<MessageQueue> clazz = (Class<MessageQueue>) Class.forName(classname);
        MessageQueue queue = ClassUtils.newInstance(clazz, new Object[]{queueName}, new Class[]{String.class});
        queueCache.put(queueName, queue);
        return queue;
    }

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, SpiderConfigException {
        System.out.println(MQFactory.getMessageQueue("/test"));
        System.out.println(MQFactory.getMessageQueue("/test"));
        System.out.println(MQFactory.getMessageQueue("/test"));

    }
}
