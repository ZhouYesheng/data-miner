package le.data.scs.spider.http.login;

import le.data.scs.spider.config.common.SpiderCookieLogin;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.http.cookie.CookieEntity;
import le.data.scs.spider.http.cookie.CookieException;

import java.util.List;

/**
 * Created by yangyong3 on 2017/2/27.
 * 实现登录一个网站
 */
public interface LoginAction {
    public List<CookieEntity> login(SpiderCookieLogin login) throws CookieException, LoginException, SpiderConfigException;
}
