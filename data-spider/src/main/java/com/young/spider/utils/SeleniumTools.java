package com.young.spider.utils;

import com.young.spider.config.ConfigFactory;
import com.young.spider.config.support.SpiderConfigException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class SeleniumTools {
    private static final Logger logger = LoggerFactory.getLogger(SeleniumTools.class);

    private static String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36";

    private static WebDriver webDriver;
    private static String screenshotsDir;
    private static boolean chromeHeadless = false;

    private static boolean debug = false;

    public synchronized static void setDebug(boolean bool) {
        debug = bool;
    }

    public synchronized static void setUserAgent(String ua) {
        userAgent = ua;
    }

    public synchronized static WebDriver getWebDriver() throws SpiderConfigException {
        if (webDriver == null) {
            boolean isRoot = System.getProperty("user.name", "").equalsIgnoreCase("root");
            boolean isWin = System.getProperty("os.name", "").toLowerCase().startsWith("win");
            screenshotsDir = ConfigFactory.getProperty("spider.crawler.webdriver.screenshot.dir", "/data/scs/screenshots");
            if (debug) {
                String logFile = ConfigFactory.getProperty("spider.crawler.webdriver.chrome.logfile", "/data/scs/logs/chromedriver.log");
                chromeHeadless = ConfigFactory.getProperty("spider.crawler.webdriver.chrome.headless", "false").equalsIgnoreCase("true");
                String driverpath = SeleniumTools.class.getResource("/").getPath() + File.separator + (isWin ? "chromedriver.exe" : "chromedriver");
                logger.info("chromeHeadless=" + chromeHeadless + ", root=" + isRoot + ", driverpath=" + driverpath + ", logFile=" + logFile);

                System.setProperty("webdriver.chrome.driver", driverpath);
                System.setProperty("webdriver.chrome.logfile", logFile);
                File logFileDir = new File(logFile).getParentFile();
                if (!logFileDir.exists()) {
                    logFileDir.mkdirs();
                }

                ChromeOptions chromeOptions = new ChromeOptions();
                if (isRoot) {
                    chromeOptions.addArguments("--no-sandbox");
                }
                if (chromeHeadless) { //没有图形界面
                    chromeOptions.addArguments("--headless", "--disable-gpu");
                }

                ChromeDriver w = new ChromeDriver(chromeOptions);
                w.setLogLevel(Level.ALL);//todo
                webDriver = w;
            } else {
                String driverpath = SeleniumTools.class.getResource("/").getPath() + File.separator + (isWin ? "phantomjs.exe" : "phantomjs");
                logger.info("driverpath=" + driverpath);
                System.setProperty("phantomjs.binary.path", driverpath);
                DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + "User-Agent", userAgent);
                capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent", userAgent);
                webDriver = new PhantomJSDriver(capabilities);
            }
            webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
            if (!chromeHeadless) {// 没有图形界面, 不支持window操作。
                webDriver.manage().window().maximize();
            }
        }
        return webDriver;
    }

    public synchronized static void takeScreenshot(String name) {
        try {
            if (null == webDriver) {
                return;
            }
            String n = String.format("%s_%s_%s.png", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), webDriver.getClass().getSimpleName(), name.trim());
            logger.info("takeScreenshot, name=" + name);

            File s = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            File dir = new File(screenshotsDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            s.renameTo(new File(dir, n));
        } catch (Throwable t) {
            // ignore
            logger.info("fail to takeScreenshot, name=" + name);
        }
    }

    public synchronized static void close() {
        if (webDriver != null) {
            webDriver.close();
        }
    }

    public synchronized static void scrollToRightIfChromeHeadless() {
        if (debug && chromeHeadless) {
            // 滚动到最右边。chrome headless模式下，不能调整window大小，导致可能没法拖拽验证，因为右边的在window以外。
            RemoteWebDriver rd = (RemoteWebDriver) webDriver;
            rd.executeScript("window.scrollBy(0,0);window.scrollBy(1000,0)");
        }
    }

    public synchronized static void quit() {
        logger.info("start to quit");
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
            logger.info("succeed to quit");
        } else {
            logger.info("webDriver is null, no need to quit");
        }
    }
}
