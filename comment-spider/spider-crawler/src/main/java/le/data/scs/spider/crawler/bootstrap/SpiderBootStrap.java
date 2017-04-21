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
 */
public class SpiderBootStrap {

    private static final Logger log = LoggerFactory.getLogger(SpiderBootStrap.class);

    public static void run(SpiderConfig config) throws SpiderConfigException, IllegalAccessException, InstantiationException, ClassNotFoundException, DistributedTaskException {
        long start = System.currentTimeMillis();
        SpiderConfig spiderConfig = null;
        if (config == null)
            spiderConfig = ConfigFactory.getConfig();
        else
            spiderConfig = config;
        SpiderThreads threads = spiderConfig.getThreads();
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
        CrawlerThreadPool threadPool = new CrawlerThreadPool(threads.getPoolsize(), threads.getPoolsize() * 2, 1, TimeUnit.HOURS, blockingQueue);
        List<AbstractProcess> processesList = new ArrayList<AbstractProcess>();
        Runtime.getRuntime().addShutdownHook(new ShutDownHook(start, threadPool, processesList));
        List<SpiderThread> spiderThreadList = threads.getThreads();
        Thread t = null;
        AbstractProcess process = null;

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
        List<SpiderTask> tasks = spiderConfig.getTasks();
        LeaderTask leaderTask = null;
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
