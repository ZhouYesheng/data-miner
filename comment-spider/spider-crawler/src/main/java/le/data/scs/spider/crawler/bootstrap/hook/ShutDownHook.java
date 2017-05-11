package le.data.scs.spider.crawler.bootstrap.hook;


import le.data.scs.common.jdbc.DBConnection;
import le.data.scs.spider.thread.AbstractProcess;
import le.data.scs.spider.thread.CrawlerThreadPool;
import le.data.scs.spider.utils.SeleniumTools;
import le.data.scs.spider.zkclient.ZKClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangyong3 on 2017/2/16.
 */
public class ShutDownHook extends Thread {
    private long starttime;

    private CrawlerThreadPool threadPool;

    private List<AbstractProcess> processList;

    private static final long one_minute = 1000 * 60;

    private static final long one_hour = one_minute * 60;

    private static final Logger logger = LoggerFactory.getLogger(ShutDownHook.class);

    public ShutDownHook(long starttime, CrawlerThreadPool threadPool, List<AbstractProcess> processList) {
        this.starttime = starttime;
        this.threadPool = threadPool;
        this.processList = processList;
    }

    /**
     * 在进程收到kill -15信号量时执行
     */
    @Override
    public void run() {
        try {
            logger.info("spider is stopping " + new Date());
            //首先停掉线程池
            threadPool.shutdown(false);
            logger.info("shut down thread pool " + new Date());
            for (AbstractProcess process : processList)
                process.stop();
            //设置线程池监控
            threadPool.monitor(10, TimeUnit.SECONDS, 10);
            //停止Zookeeper client
            ZKClientFactory.getZKClient().close();
            logger.info("Zookeeper client closed");
            //停止Selenum客户端
            SeleniumTools.quit();
            logger.info("SeleniumTools quit");
            //关闭数据库连接
            DBConnection.close();
            logger.info("mysql connection close");
            long duration = System.currentTimeMillis() - starttime;
            if (duration < one_minute) {
                logger.info("log spider is stopped, now time is " + new Date() + ", spider run duration " + (duration / one_minute) + " minutes");
            } else {
                logger.info("log spider is stopped, now time is " + new Date() + ", spider run duration " + (duration / one_hour) + " hours");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
