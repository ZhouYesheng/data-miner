package le.data.scs.common.entity.meta.comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangyong3 on 2017/3/8.
 */
public class CommentLog implements Serializable{

    private String url;

    private String productId;

    private String md5;

    private List<String> tags;

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
