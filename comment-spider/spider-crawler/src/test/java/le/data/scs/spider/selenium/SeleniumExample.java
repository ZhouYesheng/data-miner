package le.data.scs.spider.selenium;

import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.utils.SeleniumTools;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Random;
import java.util.Set;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class SeleniumExample {
    //    private static final Logger logger = LoggerFactory.getLogger(SeleniumExample.class);
    private static final Logger logger = Logger.getLogger(SeleniumExample.class);

    public static void main(String[] args) throws InterruptedException, SpiderConfigException {
        String user = "mahan123";
        String pass = "ceshi123";
        SeleniumTools.setDebug(true);
        WebDriver driver = SeleniumTools.getWebDriver();
        Thread.sleep(2000);
        driver.get("https://login.taobao.com/member/login.jhtml");
        Thread.sleep(2000);
        WebElement login = driver.findElement(By.cssSelector("a.forget-pwd.J_Quick2Static"));
        logger.info(login);
        login.click();
        Thread.sleep(2000);
        waitForYanzhengma(driver);
        WebElement username = driver.findElement(By.id("TPL_username_1"));
        logger.info(username.toString());
        click(username, driver);
        username.sendKeys(user);
        Thread.sleep(2000);
        randomMoveAround(driver);
        WebElement password = driver.findElement(By.id("TPL_password_1"));
        logger.info(password.toString());
        click(password, driver);
        password.sendKeys(pass);
        Thread.sleep(2000);
        randomMoveAround(driver);
        Actions actions = new Actions(driver);
        WebElement yanzhengma = driver.findElement(By.cssSelector("span#nc_1_n1z.nc_iconfont.btn_slide"));
        boolean yanzhengmaDisplayed = null != yanzhengma && yanzhengma.isDisplayed();
//        actions.clickAndHold(yanzhengma).moveByOffset(20,20).perform();
        logger.info("yanzhengmaDisplayed=" + yanzhengmaDisplayed);
        if (yanzhengmaDisplayed) {
//            logger.info("start move mouse");
//            for (int i = 0; i < 20; i++) {
//                Random r = new Random();
//                boolean b = r.nextBoolean();
//                int x = r.nextInt(200) * (b ? -1 : 1);
//                int y = r.nextInt(100) * (b ? -1 : 1);
//                int pause = r.nextInt(200);
//                logger.info("move mouse, x=" + x + ", y=" + y + ", pause=" + pause);
//                actions.moveByOffset(x, y).moveToElement(yanzhengma).moveByOffset(i + 20, i % 10).click().click(yanzhengma).pause(pause);
//            }
            randomMoveAround(driver);

            logger.info("start to drag yanzhengma");
            Actions tickActions = new Actions(driver);
            actions.clickAndHold(yanzhengma).pause(300);
            int offset = 300;
            int stepBase = 5;


            int xstep = new Random().nextInt(50);
            for (int i = 0; i < 300; i += xstep) {
                int y = new Random().nextInt(5);
                int ystep = 0 == i ? 5 : (i % 2 == 0 ? y : -1 * y);
//                ystep = new Random().nextInt(5) * (i%10 == 0? -1: 1);
                int pause = new Random().nextInt(20);
                logger.info("xstep=" + xstep + ", ystep=" + ystep + ", pause=" + pause);
                actions.moveByOffset(xstep, ystep).pause(pause);
                xstep = new Random().nextInt(50);
                Thread.sleep(pause);
            }
            actions.release().perform();
            logger.info("succeed to  drag yanzhengma");
        }
        Thread.sleep(3000);
//        driver.findElement(By.cssSelector("button#J_SubmitStatic.J_Submit")).click();
        Thread.sleep(3000);
        Thread.sleep(3000);
        //logger.info(driver.getPageSource());
        Set<Cookie> cookieSet = driver.manage().getCookies();
        StringBuffer sb = new StringBuffer();
        for (Cookie cookie : cookieSet) {
            sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
        }
        logger.info(sb.toString());
//        driver.close();
//        driver.quit();
    }

    // 多切换几次username的值就会出现滑块验证码
    public static void waitForYanzhengma(WebDriver driver) throws InterruptedException {
        String user = "wangfeng";
        while (!isYanzhengmaDisplayed(driver)) {
            randomMoveAround(driver);
            WebElement username = driver.findElement(By.id("TPL_username_1"));
            click(username, driver);
            username.clear();
            username.sendKeys(user + new Random().nextInt(1000));

            WebElement password = driver.findElement(By.id("TPL_password_1"));
            click(password, driver);
            Thread.sleep(2000);
        }
        WebElement username = driver.findElement(By.id("TPL_username_1"));
        username.clear();
    }

    public static boolean isYanzhengmaDisplayed(WebDriver driver) {
        WebElement yanzhengma = driver.findElement(By.cssSelector("span#nc_1_n1z.nc_iconfont.btn_slide"));
        boolean yanzhengmaDisplayed = null != yanzhengma && yanzhengma.isDisplayed();
        logger.info("yanzhengmaDisplayed=" + yanzhengmaDisplayed);
        return yanzhengmaDisplayed;
    }

    public static void click(WebElement element, WebDriver driver) throws InterruptedException {
        Random r = new Random();
        Thread.sleep(r.nextInt(100));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().pause(r.nextInt(500)).perform();
    }

    public static void randomMoveAround(WebDriver driver) {
        logger.info("start randomMoveAround");
        WebElement username = driver.findElement(By.id("TPL_username_1"));
        WebElement password = driver.findElement(By.id("TPL_password_1"));
        Actions actions = new Actions(driver);
        for (int i = 0; i < 10; i++) {
            WebElement yanzhengma = driver.findElement(By.cssSelector("span#nc_1_n1z.nc_iconfont.btn_slide"));
            boolean yanzhengmaDisplayed = null != yanzhengma && yanzhengma.isDisplayed();
            Random r = new Random();
            boolean b = r.nextBoolean();
            int x = r.nextInt(200) * (b ? -1 : 1);
            int y = r.nextInt(100) * (b ? -1 : 1);
            int pause = r.nextInt(100);
            logger.info("randomMoveAround, x=" + x + ", y=" + y + ", pause=" + pause);
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
