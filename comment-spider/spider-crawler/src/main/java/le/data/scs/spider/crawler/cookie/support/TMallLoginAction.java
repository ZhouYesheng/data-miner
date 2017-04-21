package le.data.scs.spider.crawler.cookie.support;

import le.data.scs.spider.config.common.SpiderCookieLogin;
import le.data.scs.spider.http.login.AbstractLogin;
import le.data.scs.spider.http.login.LoginException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TMallLoginAction extends AbstractLogin {

    private static final Logger logger = LoggerFactory.getLogger(TMallLoginAction.class);

    @Override
    protected void init(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        String loginPageCss = login.getPramsMapping().get("spider.crawler.login.tmall.loginpagecss");
        WebElement webElement = webDriver.findElement(By.cssSelector(loginPageCss));
        if (webElement.isDisplayed()) {
            webElement.click();
        }
    }

    @Override
    protected WebElement getUsername(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        String usernameCss = login.getPramsMapping().get("spider.crawler.login.tmall.usercss");
        return webDriver.findElement(By.id(usernameCss));
    }

    @Override
    protected WebElement getPassword(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        String passCss = login.getPramsMapping().get("spider.crawler.login.tmall.passcss");
        return webDriver.findElement(By.id(passCss));
    }

    @Override
    protected WebElement getSubmit(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        String submit = login.getPramsMapping().get("spider.crawler.login.tmall.submitcss");
        return webDriver.findElement(By.cssSelector(submit));
    }

    @Override
    protected boolean isNeedCode(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        WebElement webElement = webDriver.findElement(By.cssSelector("span.nc-lang-cnt"));
        return webElement.isDisplayed();
    }

    @Override
    protected WebElement validateCode(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        logger.info("start to validateCode");
        int[] xs = new int[]{3, 6, 10, 13, 15, 20, 30, 35, 40, 40, 40, 40, 40};
        WebElement captcha = webDriver.findElement(By.cssSelector("span#nc_1_n1z.nc_iconfont.btn_slide"));
        WebElement captchaParent = webDriver.findElement(By.id("nc_1_n1t"));
        long width = captchaParent.getSize().getWidth();
        Actions actions = new Actions(webDriver);
        actions.clickAndHold(captcha).pause(500);

        Random r = new Random();
        int idx = 0;
        int xstep = 0;
        for (int i = 0; i < width; i += xstep) {
            xstep = xs[idx++ % xs.length] + r.nextInt(5);
            int y = r.nextInt(15);
            int ystep = 0 == i ? 5 : (i % 2 == 0 ? y : -1 * y);
            int pause = r.nextInt(200);
            actions.moveByOffset(xstep, ystep).pause(pause);
        }
        long start = System.currentTimeMillis();
        actions.perform();
        long cost = System.currentTimeMillis() - start;
        logger.info("succeed to validateCode, drag count=" + idx + ", cost=" + cost + "ms");
        return captcha;
    }

    @Override
    protected boolean isLogined(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        try {
            this.getUsername(webDriver, login);
            this.getPassword(webDriver, login);
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    @Override
    protected void finish(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {

    }

    @Deprecated
    private void randomMoveAround(WebDriver driver) {
        logger.info("start randomMoveAround");
        WebElement username = driver.findElement(By.id("TPL_username_1"));
        WebElement password = driver.findElement(By.id("TPL_password_1"));

        for (int i = 0; i < 10; i++) {
            WebElement yanzhengma = driver.findElement(By.cssSelector("span#nc_1_n1z.nc_iconfont.btn_slide"));
            boolean yanzhengmaDisplayed = null != yanzhengma && yanzhengma.isDisplayed();

            Random r = new Random();
            boolean b = r.nextBoolean();
            int x = r.nextInt(200) * (b ? -1 : 1);
            int y = r.nextInt(100) * (b ? -1 : 1);
            int pause = r.nextInt(200);
            logger.info("randomMoveAround, x=" + x + ", y=" + y + ", pause=" + pause);
            Actions actions = new Actions(driver);
            if (b) {
                actions.moveByOffset(x, y).moveToElement(username).moveByOffset(i + 20, i % 10).click().pause(pause).click(username).pause(pause);
                if (yanzhengmaDisplayed) {
                    actions.moveByOffset(x, y).moveToElement(yanzhengma).moveByOffset(i + 20, i % 10).click().pause(pause).click(yanzhengma).pause(pause);
                }
            } else {
                actions.moveByOffset(x, y).moveToElement(password).moveByOffset(i + 20, i % 10).click().pause(pause).click(password).pause(pause);
            }
            actions.perform();
        }
        logger.info("succeed randomMoveAround");
    }

}
