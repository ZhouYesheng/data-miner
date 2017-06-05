package le.data.scs.spider.crawler.entity.common;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/2/16.
 * 数据抓取返回结果
 */
public class CrawlerEntity<T> implements Serializable{
    //元数据
    private T meta;
    //报文数据
    private String body;

    public T getMeta() {
        return meta;
    }

    public void setMeta(T meta) {
        this.meta = meta;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
