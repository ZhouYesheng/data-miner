package le.data.scs.spider.crawler.entity.common;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/2/16.
 * html解析结果
 */
public class ParserEntity<DATA> implements Serializable {

    //解析结果数据
    private DATA data;
    //解析状态
    private int status;
    //分页信息
    private PageEntity page;

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PageEntity getPage() {
        return page;
    }

    public void setPage(PageEntity page) {
        this.page = page;
    }
}
