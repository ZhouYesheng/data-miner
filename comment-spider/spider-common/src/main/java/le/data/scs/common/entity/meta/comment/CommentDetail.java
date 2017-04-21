package le.data.scs.common.entity.meta.comment;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/3/8.
 */
public class CommentDetail implements Serializable{

    private String createTime;

    private String text;

    private Integer score;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
