package le.data.scs.spider.crawler.http;

import le.data.scs.spider.http.HttpWalker;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.http.support.HttpClientWalker;

/**
 * Created by yangyong3 on 2017/2/23.
 * HttpWalker 工厂类
 */
public class HttpWalkerFactory {

    //创建一个HttpWalker并返回给使用者
    public static HttpWalker getHttpWalker(CrawlerType type){
        return new HttpClientWalker();
    }
}
