package le.data.scs.common.entity.meta.tmall;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/3/1.
 */
public class TMallNickEntity implements Serializable{

    private String nickUrl;

    private String start;

    private String end;

    public String getNickUrl() {
        return nickUrl;
    }

    public void setNickUrl(String nickUrl) {
        this.nickUrl = nickUrl;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
