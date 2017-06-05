package le.data.scs.spider.config.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/2/22.
 * 对应配置文件中的property标签
 */
@XStreamAlias("property")
public class SpiderProperty implements Serializable{

    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String value;
    @XStreamAsAttribute
    private String desc;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
