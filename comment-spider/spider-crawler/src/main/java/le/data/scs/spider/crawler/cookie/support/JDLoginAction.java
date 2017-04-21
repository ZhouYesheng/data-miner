package le.data.scs.spider.crawler.cookie.support;

import le.data.scs.spider.config.common.SpiderCookieLogin;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.crawler.http.HttpWalkerFactory;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.http.login.AbstractLogin;
import le.data.scs.spider.http.login.LoginException;
import le.data.scs.spider.utils.CommandTools;
import le.data.scs.spider.utils.MD5;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class JDLoginAction extends AbstractLogin {
    @Override
    protected void init(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        String loginPageCss = login.getPramsMapping().get("spider.crawler.login.jd.loginpagecss");
        WebElement webElement = webDriver.findElement(By.cssSelector(loginPageCss));
        if (webElement.isDisplayed())
            webElement.click();
    }

    @Override
    protected WebElement getUsername(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        String usernameCss = login.getPramsMapping().get("spider.crawler.login.jd.usercss");
        return webDriver.findElement(By.id(usernameCss));
    }

    @Override
    protected WebElement getPassword(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        String passCss = login.getPramsMapping().get("spider.crawler.login.jd.passcss");
        return webDriver.findElement(By.id(passCss));
    }

    @Override
    protected WebElement getSubmit(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        String submit = login.getPramsMapping().get("spider.crawler.login.jd.submitcss");
        return webDriver.findElement(By.cssSelector(submit));
    }


    @Override
    protected boolean isNeedCode(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        WebElement element = webDriver.findElement(By.id("o-authcode"));
        return element.isDisplayed();
    }

    @Override
    protected WebElement validateCode(WebDriver webDriver, SpiderCookieLogin login) throws LoginException {
        //识别验证码
        WebElement webElement = webDriver.findElement(By.id("JD_Verification1"));
        WebElement codeElement = webDriver.findElement(By.id("authcode"));
        String codeUrl = "https:" + webElement.getAttribute("src2") + "&yys=" + System.currentTimeMillis();
        Set<Cookie> cookieSet = webDriver.manage().getCookies();
        Map<String, String> header = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : cookieSet) {
            sb.append(cookie.getName() + "=" + cookie.getValue() + ";");
        }
        header.put("Cookie", sb.toString());
        Response response = null;
        File downloadImage = null;
        String code = null;
        try {
            response = HttpWalkerFactory.getHttpWalker(CrawlerType.JD).get(new Request(codeUrl, header));
            downloadImage = new File(login.getType() + "_" + MD5.md5(login.getUsername()) + "_" + System.currentTimeMillis() + ".jpg");
            IOUtils.copy(response.getInputStream(), new FileOutputStream(downloadImage));
            code = recognizeImage(downloadImage);
            codeElement.sendKeys(code);
        } catch (Exception e) {
            throw new LoginException(e);
        }
        return null;
    }

    private String recognizeImage(File file) throws IOException, InterruptedException {
        String command = "tesseract " + file.getPath() + " " + file.getPath() + "_result" + " -l eng";
        CommandTools.process(command);
        return FileUtils.readFileToString(new File(file.getPath() + "_result"));
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
        webDriver.get(login.getPramsMapping().get("spider.crawler.login.jd.newpage"));
    }
}
