package le.data.scs.spider.crawler.crawler;

import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.crawler.thread.tmall.TmallCommentCrawlerThread;

/**
 * Created by yangyong3 on 2017/3/9.
 */
public class TmallCommentThreadTest {
    public static void main(String[] args) throws SpiderConfigException {
        for(int i=0;i<2;i++){
            new Thread(new TmallCommentCrawlerThread()).start();
        }
    }
}
