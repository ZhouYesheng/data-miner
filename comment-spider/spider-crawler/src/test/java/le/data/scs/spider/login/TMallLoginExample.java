package le.data.scs.spider.login;

import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.common.SpiderCookieLogin;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.http.cookie.CookieEntity;
import le.data.scs.spider.http.cookie.CookieException;

import le.data.scs.spider.http.login.LoginAction;
import le.data.scs.spider.crawler.cookie.support.TMallLoginAction;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.http.login.LoginException;
import le.data.scs.spider.utils.JsonUtils;
import le.data.scs.spider.utils.SeleniumTools;

import java.io.IOException;
import java.util.List;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TMallLoginExample {
    public static void main(String[] args) throws SpiderConfigException, CookieException, IOException, InterruptedException, LoginException {
        SeleniumTools.setDebug(true);
        LoginAction action = new TMallLoginAction();
        SpiderCookieLogin login = ConfigFactory.getLogin(CrawlerType.TMALL.toString());
        List<CookieEntity> cookieEntityList = action.login(login);
        System.out.println(JsonUtils.toJson(cookieEntityList));
        Thread.sleep(3000);
        SeleniumTools.quit();
    }
}
