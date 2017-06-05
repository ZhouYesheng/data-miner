package le.data.scs.common.entity.meta.jd;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/3/8.
 * 京东评论种子实体
 */
public class JDCommentEntity implements Serializable {
    //商品的主页url
    private String pageUrl;
    //商品名称
    private String pName;
    //md5
    private String md5;
    //商品ID
    private String productId;
    //排序规则，该字段是京东平台使用，采用默认值即可
    private String sortType = "6";
    //当前页码
    private Integer page = 1;
    //每页数据条数
    private Integer pageSize = 10;

    private String isShadowSku ="0";

    private String score = "1";

    private String countColumn;

    public JDCommentEntity(String pageUrl, String pName, String md5, String productId,String score) {
        this.pageUrl = pageUrl;
        this.pName = pName;
        this.md5 = md5;
        this.productId = productId;
        this.score = score;
    }

    public String getCountColumn() {
        return countColumn;
    }

    public void setCountColumn(String countColumn) {
        this.countColumn = countColumn;
    }

    public JDCommentEntity(){}

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getIsShadowSku() {
        return isShadowSku;
    }

    public void setIsShadowSku(String isShadowSku) {
        this.isShadowSku = isShadowSku;
    }
}
