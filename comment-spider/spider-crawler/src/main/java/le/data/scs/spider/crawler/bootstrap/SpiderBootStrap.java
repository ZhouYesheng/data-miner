package le.data.scs.spider.crawler.bootstrap;



import le.data.scs.spider.config.ConfigFactory;
import le.data.scs.spider.config.common.SpiderConfig;
import le.data.scs.spider.config.common.SpiderTask;
import le.data.scs.spider.config.common.SpiderThread;
import le.data.scs.spider.config.common.SpiderThreads;
import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.distribution.task.DistributedTaskException;
import le.data.scs.spider.distribution.task.leadertask.LeaderTask;
import le.data.scs.spider.crawler.bootstrap.hook.ShutDownHook;
import le.data.scs.spider.thread.AbstractProcess;
import le.data.scs.spider.thread.CrawlerThreadPool;
import le.data.scs.spider.utils.ClassUtils;
import le.data.scs.spider.utils.SeleniumTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangyong3 on 2017/2/16.
 * 爬虫主引导类，启动类
 */
public class SpiderBootStrap {

    private static final Logger log = LoggerFactory.getLogger(SpiderBootStrap.class);

    /**
     * 爬虫启动
     * @param config
     * @throws SpiderConfigException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws DistributedTaskException
     */
    public static void run(SpiderConfig config) throws SpiderConfigException, IllegalAccessException, InstantiationException, ClassNotFoundException, DistributedTaskException {
        long start = System.currentTimeMillis();
        SpiderConfig spiderConfig = null;
        /**
         * 读取配置文件spider-config.xml
         */
        if (config == null)
            spiderConfig = ConfigFactory.getConfig();
        else
            spiderConfig = config;
        //获取配置文件中的线程配置
        SpiderThreads threads = spiderConfig.getThreads();
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
        //创建线程池
        CrawlerThreadPool threadPool = new CrawlerThreadPool(threads.getPoolsize(), threads.getPoolsize() * 2, 1, TimeUnit.HOURS, blockingQueue);
        List<AbstractProcess> processesList = new ArrayList<AbstractProcess>();
        //设置停止hook线程
        Runtime.getRuntime().addShutdownHook(new ShutDownHook(start, threadPool, processesList));
        List<SpiderThread> spiderThreadList = threads.getThreads();
        Thread t = null;
        AbstractProcess process = null;

        /**
         * 启动抓取线程
         */
        for (SpiderThread thread : spiderThreadList) {
            if (thread.isUse()) {
                for (int i = 0; i < thread.getSize(); i++) {
                    process = ClassUtils.newInstance(thread.getClassname());
                    t = new Thread(process);
                    t.setName(thread.getName());
                    threadPool.execute(t);
                    processesList.add(process);
                    log.info("start thread " + t.getName() + " index is -[" + i + "]");
                }
            }
        }
        /**
         * 获取zookeeper LeaderTask任务
         */
        List<SpiderTask> tasks = spiderConfig.getTasks();
        LeaderTask leaderTask = null;
        //启动LeaderTask任务
        for (SpiderTask task : tasks) {
            if (task.isUse()) {
                leaderTask = ClassUtils.newInstance(task.getClassname());
                leaderTask.runTask(false);
                log.info("start leader task " + task.getName());
            }
        }
    }

    public static void main(String[] args) throws SpiderConfigException, ClassNotFoundException, InstantiationException, DistributedTaskException, IllegalAccessException {
        SeleniumTools.setDebug(true);
        SpiderBootStrap bootStrap = new SpiderBootStrap();
        bootStrap.run(null);
    }
}
