package le.data.scs.spider.config;

import le.data.scs.spider.utils.ClassUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by young.yang on 2017/2/25.
 */
public abstract class BaseFactory {

    private static final Map<String,Object> instanceCache = new Hashtable<String,Object>();

    public static final <T>  T getInstance(String classname) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(instanceCache.containsKey(classname)){
            return (T) instanceCache.get(classname);
        }
        T t = ClassUtils.newInstance(classname);
        instanceCache.put(classname,t);
        return t;
    }

    public static final <T> T getInstance(Class clazz) throws IllegalAccessException, InstantiationException {
        if(instanceCache.containsKey(clazz.getName())){
            return (T) instanceCache.get(clazz.getName());
        }
        T t = (T) clazz.newInstance();
        instanceCache.put(clazz.getName(),t);
        return t;
    }
}
