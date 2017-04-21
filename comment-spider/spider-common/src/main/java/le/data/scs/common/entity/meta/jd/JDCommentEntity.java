package le.data.scs.common.entity.meta.jd;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/3/8.
 */
public class JDCommentEntity implements Serializable {
    private String pageUrl;

    private String pName;

    private String md5;

    private String productId;

    private String sortType = "6";

    private Integer page = 1;

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
