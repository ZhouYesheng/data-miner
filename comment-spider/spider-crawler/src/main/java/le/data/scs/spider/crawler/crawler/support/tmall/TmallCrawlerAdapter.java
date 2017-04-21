package le.data.scs.spider.crawler.crawler.support.tmall;

import le.data.scs.spider.http.Request;
import le.data.scs.spider.crawler.crawler.common.CrawlerAdapter;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/28.
 */
public abstract class TmallCrawlerAdapter<Meta> extends CrawlerAdapter<Meta> {
    public TmallCrawlerAdapter(CrawlerType type) {
        super(type);
    }

    public TmallCrawlerAdapter(){
        this(CrawlerType.TMALL);
    }

    protected String getUrl(String baseUrl, String params, String param_value, String cookie) throws IOException {
        StringBuilder url = new StringBuilder();
        url.append(baseUrl);
        url.append("&_tb_token_=" + getToken(cookie));
        url.append("&_input_charset=utf-8");
        url.append("&"+params+"="+ param_value);
        url.append("&_="+System.currentTimeMillis());
        return url.toString();
    }

    private String getToken(String cookie) throws IOException {
        String[] temp = cookie.split(";");
        if (temp == null||temp.length==0)
            return null;
        String[] kvs = null;
        for (String line : temp) {
            kvs = line.split("=");
            if (kvs[0].trim().equals("_tb_token_")) {
                return kvs[1].trim();
            }
        }
        return null;
    }

    protected Request baseReqeust(String baseUrl,String params,String param_value) throws CrawlerException {
        String cookie = findCookie(this.type);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Cookie", cookie);
        String url = null;
        try {
            url = getUrl(baseUrl,params,param_value, cookie);
        } catch (IOException e) {
            throw new CrawlerException("get url error",e);
        }
        return new Request(url, header,null,"gb2312");
    }
}
