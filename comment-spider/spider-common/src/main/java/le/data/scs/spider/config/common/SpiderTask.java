package le.data.scs.spider.config.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/3/1.
 * 对应配置文件中的task标签
 */
@XStreamAlias("task")
public class SpiderTask implements Serializable{

    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String classname;
    @XStreamAsAttribute
    private boolean use;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }


    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }
}
