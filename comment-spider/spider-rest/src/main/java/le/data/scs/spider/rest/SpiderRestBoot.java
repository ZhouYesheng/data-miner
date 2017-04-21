package le.data.scs.spider.rest;

import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.crawler.bootstrap.SpiderBootStrap;
import le.data.scs.spider.distribution.task.DistributedTaskException;
import le.data.scs.spider.utils.SeleniumTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by yangyong3 on 2017/3/10.
 */
@SpringBootApplication
@ComponentScan("le.data.scs.spider.rest")
public class SpiderRestBoot {
    public static void main(String[] args) throws SpiderConfigException, ClassNotFoundException, InstantiationException, DistributedTaskException, IllegalAccessException {
        SpringApplication.run(SpiderRestBoot.class, args);
        SeleniumTools.setDebug(true);
        SpiderBootStrap.run(null);
    }
}
