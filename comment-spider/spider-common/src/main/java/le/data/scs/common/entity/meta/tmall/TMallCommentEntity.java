package le.data.scs.common.entity.meta.tmall;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangyong3 on 2017/3/9.
 * 天猫数据评论实体类
 */
public class TMallCommentEntity implements Serializable {

    //商品主页url
    private String url;
    //md5
    private String md5;
    //商品名称
    private String pName;
    //天猫平台使用参数，默认值即可
    private String tbpm;
    //商品itemId
    private String itemId;
    //天猫平台使用参数，默认值即可
    private String spuId;
    //天猫平台使用参数，默认值即可
    private String sellerId;
    //天猫平台使用参数，默认值即可
    private String order;
    //当前页码
    private Integer currentPage;
    //天猫平台使用参数，默认值即可
    private String append;
    //天猫平台使用参数，默认值即可
    private String content;
    //天猫平台使用参数，默认值即可
    private Boolean isAll;
    //天猫平台使用参数，默认值即可
    private Boolean isInner;
    //天猫平台使用参数，默认值即可
    private String type;

    private Long t = System.currentTimeMillis();

    private List<String> tags;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public enum TMallCommentType {
        COMMENT_CIYUN, COMMENT_DETAIL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTbpm() {
        return tbpm;
    }

    public void setTbpm(String tbpm) {
        this.tbpm = tbpm;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getAll() {
        return isAll;
    }

    public void setAll(Boolean all) {
        isAll = all;
    }

    public Boolean getInner() {
        return isInner;
    }

    public void setInner(Boolean inner) {
        isInner = inner;
    }

    public Long getT() {
        return t;
    }

    public void setT(Long t) {
        this.t = t;
    }
}
