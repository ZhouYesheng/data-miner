package le.data.scs.common.entity.meta.comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangyong3 on 2017/3/8.
 * 评论实体类
 */
public class CommentLog implements Serializable{

    //该评论的url
    private String url;
    //该评论的商品id
    private String productId;
    //md5
    private String md5;
    //该评论中的关键词
    private List<String> tags;
    //评论明细
    private List<CommentDetail> details;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public List<CommentDetail> getDetails() {
        return details;
    }

    public void setDetails(List<CommentDetail> details) {
        this.details = details;
    }
}
