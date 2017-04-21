package le.data.scs.spider.waiter;

import le.data.scs.spider.http.HttpWalker;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.http.cookie.CookieException;
import le.data.scs.spider.crawler.cookie.SpiderCookieFactory;
import le.data.scs.spider.http.support.HttpClientWalker;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/21.
 */
public class TmallCustomerListExample {

    private static HttpWalker httpWalker = new HttpClientWalker();

    public void getCustomerList() throws IOException, CookieException {
        String url = "https://zizhanghao.taobao.com/subaccount/monitor/chatRecordHtml.htm?action=/subaccount/monitor/ChatRecordQueryAction&eventSubmitDoQueryChatContent=anything&_tb_token_=3e335dee7113&_input_charset=utf-8&chatRelationQuery=%7B%22employeeNick%22%3A%22cntaobao%E4%B9%90%E8%A7%86%E5%AE%98%E6%96%B9%E6%97%97%E8%88%B0%E5%BA%97%22%2C%22customerNick%22%3A%22cntaobao13912616685%E7%A8%8B%22%2C%22start%22%3A%222017-02-27%22%2C%22end%22%3A%222017-02-27%22%2C%22beginKey%22%3Anull%2C%22endKey%22%3Anull%2C%22employeeAll%22%3Afalse%2C%22customerAll%22%3Afalse%7D&_=1488267563797";
        Map<String, String> header = new HashMap<>();
        header.put("Cookie", SpiderCookieFactory.getCookie(CrawlerType.TMALL));
        header.put("content-language","zh-CN");
        header.put("content-type","utf-8");
        Response response = httpWalker.get(new Request(url,header,null,"gb2312"));
        System.out.println(response.getContent());
    }

    public static void main(String[] args) throws IOException, CookieException {
        TmallCustomerListExample example = new TmallCustomerListExample();
        example.getCustomerList();
    }
}
