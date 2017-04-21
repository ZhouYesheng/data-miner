package le.data.scs.spider.login;

import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.common.SpiderCookieLogin;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.http.cookie.CookieEntity;
import le.data.scs.spider.http.cookie.CookieException;
import le.data.scs.spider.http.login.LoginAction;
import le.data.scs.spider.crawler.cookie.support.JDLoginAction;
import le.data.scs.spider.crawler.cookie.support.TMallLoginAction;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.http.login.LoginException;
import le.data.scs.spider.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class LoginExample {
    public static void main(String[] args) throws SpiderConfigException, CookieException, IOException, LoginException {
        SpiderCookieLogin jdLogin = ConfigFactory.getLogin(CrawlerType.JD.toString());
        SpiderCookieLogin tmallLogin = ConfigFactory.getLogin(CrawlerType.TMALL.toString());
        LoginAction jdAction = new JDLoginAction();
        LoginAction tmallAction = new TMallLoginAction();
        List<CookieEntity> jdCookies = jdAction.login(jdLogin);
        List<CookieEntity> tmallCookies = tmallAction.login(tmallLogin);
        System.out.println(JsonUtils.toJson(jdCookies));
        System.out.println(JsonUtils.toJson(tmallCookies));
    }
}
