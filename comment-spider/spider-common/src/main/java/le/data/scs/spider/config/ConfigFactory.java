package le.data.scs.spider.config;

import le.data.scs.spider.config.common.SpiderConfig;
import le.data.scs.spider.config.common.SpiderCookieLogin;
import le.data.scs.spider.config.common.SpiderProperty;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.utils.ClassUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * Created by yangyong3 on 2017/2/22.
 */
public class ConfigFactory {

    private static final Logger log = LogManager.getLogger(ConfigFactory.class);

    private static ConfigStorage storage;

    private static SpiderConfig spiderConfig;

    private static final Map<String, String> properties = new Hashtable<String, String>();

    private static final Map<String, SpiderCookieLogin> loginCache = new Hashtable<String, SpiderCookieLogin>();

    private static synchronized void initConfigStorage() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (storage == null) {
            log.info("init config storage classname = [" + GlobalConfig.SPIDER_CONFIG_CLASSNAME + "] config path is [" + GlobalConfig.SPIDER_CONFIG_PATH + "]");
            storage = ClassUtils.newInstance(GlobalConfig.SPIDER_CONFIG_CLASSNAME, new Object[]{GlobalConfig.SPIDER_CONFIG_PATH}, new Class[]{String.class});
        }
    }

    public static SpiderConfig getConfig() throws SpiderConfigException {
        if (spiderConfig != null)
            return spiderConfig;
        if (storage != null)
            spiderConfig = storage.getSpiderConfig();
        try {
            initConfigStorage();
            spiderConfig = storage.getSpiderConfig();
        } catch (Exception e) {
            throw new SpiderConfigException("get spider config error storage is [" + storage + "]", e);
        }
        if (spiderConfig != null) {
            for (SpiderProperty property : getConfig().getProperties()) {
                properties.put(property.getName(), property.getValue());
            }
        }
        return spiderConfig;
    }

    public static String getProperty(String key) throws SpiderConfigException {
        if (properties != null && !properties.isEmpty())
            return properties.get(key);
        getConfig();
        return properties.get(key);
    }

    public static String getProperty(String key, String defaultValue) throws SpiderConfigException {
        String val = getProperty(key);
        if (StringUtils.isBlank(val)) {
            return defaultValue;
        } else {
            return val;
        }
    }


    public static final void sleepTime() {
        try {
            int time = 30000 + new Random().nextInt(10000);
            log.info("sleep time [" + time + "]");
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static SpiderCookieLogin getLogin(String type) throws SpiderConfigException {
        if (loginCache != null && !loginCache.isEmpty())
            return loginCache.get(type);
        if (!CollectionUtils.isEmpty(getConfig().getSpiderCookie().getLogins())) {
            for (SpiderCookieLogin login : getConfig().getSpiderCookie().getLogins()) {
                loginCache.put(login.getType(), login);
            }
        }
        return loginCache.get(type);
    }
}
