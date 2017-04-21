package le.data.scs.spider.waiter;

import le.data.scs.spider.http.HttpWalker;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.http.support.HttpClientWalker;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/16.
 */
public class HttpWalkerTest {

    private HttpWalker walker = new HttpClientWalker();

    public void testwalk(String url) throws IOException {
        Request request = new Request(url);
        Response response = walker.get(request);
        System.out.println(response.getContent());
        System.out.println(response.getStatusCode());
        System.out.println(IOUtils.toString(response.getInputStream()));
    }

    public static void main(String[] args) throws IOException {
        String url = "http://www.baidu.com";
        HttpWalkerTest test = new HttpWalkerTest();
        test.testwalk(url);
    }
}
