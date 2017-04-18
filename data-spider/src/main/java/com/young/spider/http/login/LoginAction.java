package com.young.spider.http.login;



import com.young.spider.config.common.SpiderCookieLogin;
import com.young.spider.config.support.SpiderConfigException;
import com.young.spider.http.cookie.CookieEntity;
import com.young.spider.http.cookie.CookieException;

import java.util.List;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public interface LoginAction {
    public List<CookieEntity> login(SpiderCookieLogin login) throws CookieException, LoginException, SpiderConfigException;
}
