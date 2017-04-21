package le.data.scs.spider.crawler.cookie.support;

import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.common.SpiderCookieLogin;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.http.cookie.CookieEntity;
import le.data.scs.spider.http.cookie.CookieException;
import le.data.scs.spider.http.login.LoginAction;
import le.data.scs.spider.utils.ClassUtils;
import le.data.scs.spider.utils.JsonUtils;
import le.data.scs.spider.zkclient.ZKClientFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public abstract class AbstractCookieStorage {

    private static final Logger log = LoggerFactory.getLogger(AbstractCookieStorage.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Map<String, LoginAction> loginCache = new Hashtable<String, LoginAction>();

    private static final String cookieLockPath = "/tmp/store/cookie";

    public abstract String readCookie(String storePath) throws CookieException;

    public abstract void writeBack(String storePath, String cookieJson) throws CookieException;

    public abstract void delCookie(String storePath) throws CookieException;

    protected String getCookie(SpiderCookieLogin login, boolean is_login) throws CookieException {
        String cookieQueueName = null;
        List<Map<String, Object>> cookieMap = new ArrayList<Map<String, Object>>();
        String cookieJson = null;
        try {
            cookieQueueName = ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + login.getQueue();
            cookieJson = readCookie(cookieQueueName);
            if (cookieJson == null && is_login) {
                cookieJson = login(login);
            }
            if (cookieJson != null) {
                cookieMap = mapper.readValue(cookieJson, new TypeReference<List<Map<String, Object>>>() {
                });
            }
        } catch (Exception e) {
            throw new CookieException("get cookie error", e);
        }
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> map : cookieMap) {
            sb.append(map.get("name").toString() + "=" + map.get("value").toString() + ";");
        }
        return sb.toString();
    }

    protected void removeCookie(SpiderCookieLogin login) throws CookieException {
        String cookieQueueName = null;
        try {
            cookieQueueName = ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + login.getQueue();
            delCookie(cookieQueueName);
        } catch (Exception e) {
            throw new CookieException("del cookie error", e);
        }
    }

    private LoginAction getLoginAction(SpiderCookieLogin login) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (loginCache.containsKey(login.getType()))
            return loginCache.get(login.getType());
        LoginAction loginAction = ClassUtils.newInstance(login.getClassname());
        loginCache.put(login.getType(), loginAction);
        return loginAction;
    }

    private String login(SpiderCookieLogin login) throws SpiderConfigException, CookieException {
        CuratorFramework client = ZKClientFactory.getZKClient();
        InterProcessMutex lock = null;
        String cookieJson = null;
        long now = System.currentTimeMillis();
        List<CookieEntity> cookieEntityList = null;
        String cookieQueueName = null;
        try {
            cookieQueueName = ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + login.getQueue();
            lock = new InterProcessMutex(client, ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + cookieLockPath);
            if (lock.acquire(2, TimeUnit.MINUTES)) {
                try {
                    cookieJson = readCookie(cookieQueueName);
                    if (cookieJson != null)
                        return cookieJson;
                    cookieEntityList = getLoginAction(login).login(login);
                    log.info("login execute result is cookie size -[" + cookieEntityList.size() + "] cost itme -" + (System.currentTimeMillis() - now));
                    cookieJson = JsonUtils.toJson(cookieEntityList);
                    if (!StringUtils.isBlank(cookieJson)) {
                        cookieQueueName = ConfigFactory.getConfig().getMessageQueue().getQueueNamePrefix() + login.getQueue();
                        writeBack(cookieQueueName, cookieJson);
                    }
                } catch (Exception e) {
                    throw new CookieException("login and get cookie error", e);
                } finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        throw new CookieException("login and get cookie error", e);
                    }
                }
            }
            return cookieJson;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CookieException("login error", e);
        }
    }

}
