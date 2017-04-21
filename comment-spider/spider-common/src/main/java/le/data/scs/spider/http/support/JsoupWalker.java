package le.data.scs.spider.http.support;

import le.data.scs.spider.http.HttpWalker;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by yangyong3 on 2016/12/1.
 */
public class JsoupWalker extends AbstractHttpWalker implements HttpWalker {

    private static final Logger log = LoggerFactory.getLogger(JsoupWalker.class);

    private String user_agent = "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2";

    private int timeout = 10000;

    public JsoupWalker(String user_agent, int timeout) {
        this.user_agent = user_agent;
        this.timeout = timeout;
    }

    public JsoupWalker() {
    }

    private Connection getConnection(Request request, Connection.Method method, Map<String, String> params) {
        Connection connection = HttpConnection.connect(request.getUrl()).timeout(timeout).ignoreContentType(true);
        if (!StringUtils.isBlank(user_agent)) {
            connection.userAgent(user_agent);
        }
 //       String cookie = null;
        if(request.getHeader()!=null&&!request.getHeader().isEmpty()){
//            cookie = request.getHeader().containsKey("Cookie")?request.getHeader().get("Cookie"):null;
//            request.getHeader().remove("Cookie");
            connection.headers(request.getHeader());
        }
//        if(cookie!=null){
//            Map<String,String> cookieMap = new HashMap<String,String>();
//            String[] temp = null;
//            String[] cookies = cookie.split(";");
//            if(cookies!=null&&cookies.length>0){
//                for(String line:cookies){
//                    temp = line.split("=");
//                    if(temp!=null&&temp.length==2){
//                        cookieMap.put(temp[0],temp[1]);
//                    }
//                }
//            }
//            connection.cookies(cookieMap);
//        }
        if (params != null && params.size() > 0) {
            connection.data(params);
        }
        return connection;
    }

    private Response sendRequest(Connection connection,Request request) throws IOException {
        Connection.Response jgoupResponse = connection.execute();
        return new Response(jgoupResponse.statusCode(), new ByteArrayInputStream(jgoupResponse.bodyAsBytes()), jgoupResponse.statusMessage(),request.getEncode());
    }


    @Override
    public Response get(Request request) throws IOException {
        checkRequest(request);
        Connection connection = getConnection(request, Connection.Method.GET, null);
        return sendRequest(connection,request);
    }

    @Override
    public Response post(Request request) throws IOException {
        checkRequest(request);
        Connection connection = getConnection(request, Connection.Method.POST, request.getParameters());
        return sendRequest(connection,request);
    }

    @Override
    public Response delete(Request request) throws IOException {
        checkRequest(request);
        Connection connection = getConnection(request, Connection.Method.DELETE, null);
        return sendRequest(connection,request);
    }
}
