package com.young.spider.http.login;


import com.young.spider.config.ConfigFactory;
import com.young.spider.config.common.SpiderCookieLogin;
import com.young.spider.config.support.SpiderConfigException;
import com.young.spider.http.cookie.CookieEntity;
import com.young.spider.http.cookie.CookieException;
import com.young.spider.utils.SeleniumTools;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public abstract class AbstractLogin implements LoginAction {
    private static final Logger logger = LoggerFactory.getLogger(AbstractLogin.class);

    protected abstract void init(WebDriver webDriver, SpiderCookieLogin login) throws LoginException;

    protected abstract WebElement getUsername(WebDriver webDriver, SpiderCookieLogin login) throws LoginException;

    protected abstract WebElement getPassword(WebDriver webDriver, SpiderCookieLogin login) throws LoginException;

    protected abstract WebElement getSubmit(WebDriver webDriver, SpiderCookieLogin login) throws LoginException;

    protected abstract boolean isNeedCode(WebDriver webDriver, SpiderCookieLogin login) throws LoginException;

    protected abstract WebElement validateCode(WebDriver webDriver, SpiderCookieLogin login) throws LoginException;

    protected abstract void finish(WebDriver webDriver, SpiderCookieLogin login) throws LoginException;

    protected abstract boolean isLogined(WebDriver webDriver, SpiderCookieLogin login) throws LoginException;

    /**
     * 1 没有验证码。
     * 2 输入完用户名、密码就出现验证码/拖拽验证。
     * 3 点登陆按钮后，出现验证码/拖拽验证。
     *
     * @param login
     * @return
     * @throws CookieException
     * @throws LoginException
     * @throws SpiderConfigException
     */
    @Override
    public List<CookieEntity> login(SpiderCookieLogin login) throws CookieException, LoginException, SpiderConfigException {
        List<CookieEntity> cookieEntityList = new ArrayList<CookieEntity>();
        try {
            WebDriver webDriver = SeleniumTools.getWebDriver();
            logger.info("start to login, type=" + login.getType() + ", url=" + login.getUrl());
            webDriver.get(login.getUrl());
            int stepSleep = login.getStepSleep();
            Thread.sleep(stepSleep);

            boolean isLogined = false;
            Integer retryCount = Integer.parseInt(ConfigFactory.getProperty("spider.crawler.login.retry.count", "3"));
            Integer retryInterval = Integer.parseInt(ConfigFactory.getProperty("spider.crawler.login.retry.interval.seconds", "5"));
            int attempt = 0;
            while (!isLogined && attempt++ < retryCount) {
                logger.info("login attempt=" + attempt + ", retryCount=" + retryCount + ", retryInterval=" + retryInterval);
                if (attempt > 1) {
                    Thread.sleep(attempt * retryInterval);
                }
                SeleniumTools.takeScreenshot(login.getType() + "_1_start");
                init(webDriver, login);
                Thread.sleep(stepSleep);
                SeleniumTools.scrollToRightIfChromeHeadless();
                SeleniumTools.takeScreenshot(login.getType() + "_2_init");
                WebElement user = getUsername(webDriver, login);
                user.clear();
                user.sendKeys(login.getUsername());
                Thread.sleep(stepSleep);
                WebElement pass = getPassword(webDriver, login);
                pass.clear();
                pass.sendKeys(login.getPassword());
                Thread.sleep(stepSleep);
                SeleniumTools.takeScreenshot(login.getType() + "_3_input");
                if (isNeedCode(webDriver, login)) {
                    validateCode(webDriver, login);
                    Thread.sleep(stepSleep);
                    SeleniumTools.takeScreenshot(login.getType() + "_4_captcha");
                }
                WebElement submit = getSubmit(webDriver, login);
                submit.click();
                Thread.sleep(stepSleep);
                SeleniumTools.takeScreenshot(login.getType() + "_5_submitted");

                isLogined = isLogined(webDriver, login);
            }
            logger.info("after login, isLogined=" + isLogined + ", url=" + login.getUrl());
            // todo throw exception if not logined

            finish(webDriver, login);
            Thread.sleep(stepSleep);
            Set<Cookie> cookieSet = webDriver.manage().getCookies();
            for (Cookie cookie : cookieSet) {
                cookieEntityList.add(new CookieEntity(cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getDomain(), cookie.getExpiry() == null ? null : cookie.getExpiry().getTime(), cookie.isSecure(), cookie.isHttpOnly()));
            }
            //退出page页面
            SeleniumTools.quit();
        } catch (InterruptedException e) {
            e.printStackTrace();
            SeleniumTools.quit();
            throw new LoginException("login error", e);
        }
        return cookieEntityList;
    }
}
