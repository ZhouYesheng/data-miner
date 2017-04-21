package le.data.scs.spider.crawler.crawler;

import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.crawler.thread.jd.JDCommentCrawlerThread;

/**
 * Created by yangyong3 on 2017/3/8.
 */
public class JDCommentThreadTest {
    public static void main(String[] args) throws SpiderConfigException {
        for(int i=0;i<2;i++){
            new Thread(new JDCommentCrawlerThread()).start();
        }
    }
}
