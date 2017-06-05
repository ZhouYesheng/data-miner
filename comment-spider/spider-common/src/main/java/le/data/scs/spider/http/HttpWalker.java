package le.data.scs.spider.http;

import java.io.IOException;

/**
 * Created by yangyong3 on 2017/2/20.
 * Http访问工具类接口
 */
public interface HttpWalker {
    /**
     * 发送get请求
     * @param request
     * @return
     * @throws IOException
     */
    public Response get(Request request) throws IOException;

    /**
     * 发送post请求
     * @param request
     * @return
     * @throws IOException
     */
    public Response post(Request request) throws IOException;

    /**
     * 发送delete请求
     * @param request
     * @return
     * @throws IOException
     */
    public Response delete(Request request) throws IOException;
}
