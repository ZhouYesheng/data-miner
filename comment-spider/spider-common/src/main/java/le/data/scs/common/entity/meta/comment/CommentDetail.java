package le.data.scs.common.entity.meta.comment;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/3/8.
 * 评论实体类
 */
public class CommentDetail implements Serializable{

    //评论时间
    private String createTime;
    //评论内容
    private String text;
    //评论分数
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
