package le.data.scs.common.entity.meta.chat;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/3/2.
 */
public class ChatDetail implements Serializable {

    public ChatDetail(String text, String createTime, String role) {
        this.text = text;
        this.createTime = createTime;
        this.role = role;
    }

    public ChatDetail() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String text = "";

    private String role;

    private String createTime;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreateTime() {
        return createTime;
    }


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
