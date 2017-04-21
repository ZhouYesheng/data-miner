package le.data.scs.spider.crawler.crawler;

import le.data.scs.spider.config.support.SpiderConfigException;
import le.data.scs.spider.crawler.thread.tmall.TmallChatCrawlerThread;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public class TmallChatThreadTest {
    public static void main(String[] args) throws SpiderConfigException {
        for(int i=0;i<1;i++){
            new Thread(new TmallChatCrawlerThread()).start();
        }
    }
}
