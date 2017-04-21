package le.data.scs.spider.selenium;

import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.utils.SeleniumTools;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.File;
import java.util.Set;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public class PhantomJSExample {
    public static void main(String[] args) throws InterruptedException, SpiderConfigException {
        String user = "乐视官方旗舰店:蒙多";
        String pass = "letv123";
        WebDriver driver = SeleniumTools.getWebDriver();
        Thread.sleep(3000);
        driver.get("https://login.taobao.com/member/login.jhtml");
        Thread.sleep(3000);
        WebElement login = driver.findElement(By.cssSelector("a.forget-pwd.J_Quick2Static"));
        System.out.println(login);
        login.click();
        Thread.sleep(3000);
        WebElement username = driver.findElement(By.id("TPL_username_1"));
        System.out.println(username.toString());
        username.sendKeys(user);
        Thread.sleep(3000);
        WebElement password = driver.findElement(By.id("TPL_password_1"));
        System.out.println(password.toString());
        password.sendKeys(pass);
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("button#J_SubmitStatic.J_Submit")).click();
        Thread.sleep(3000);
        Actions actions=new Actions(driver);

        actions.click().moveByOffset(1,1).perform();
        //System.out.println(driver.getPageSource());
        Set<Cookie> cookieSet = driver.manage().getCookies();
        StringBuffer sb = new StringBuffer();
        for(Cookie cookie:cookieSet){
            sb.append(cookie.getName()+"="+cookie.getValue()+";");
        }
        System.out.println(sb.toString());
        driver.close();
    }
}
