package le.data.scs.spider.config.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/22.
 * 对应配置文件中的login标签
 */
@XStreamAlias("login")
public class SpiderCookieLogin implements Serializable{
    @XStreamAsAttribute
    private String type;
    @XStreamAsAttribute
    private String url;
    @XStreamAsAttribute
    private String classname;

    @XStreamAsAttribute
    private String queue;

    @XStreamAsAttribute
    private String username;
    @XStreamAsAttribute
    private String password;

    @XStreamAsAttribute
    private Integer stepSleep = 3000;

    private List<SpiderParam> params;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Integer getStepSleep() {
        return stepSleep;
    }

    public void setStepSleep(Integer stepSleep) {
        this.stepSleep = stepSleep;
    }

    public List<SpiderParam> getParams() {
        return params;
    }

    public void setParams(List<SpiderParam> params) {
        this.params = params;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String,String> getPramsMapping(){
        Map<String,String> mapping = new HashMap<String,String>();
        if(!CollectionUtils.isEmpty(this.getParams())){
            for(SpiderParam param:this.getParams()){
                mapping.put(param.getName(),param.getValue());
            }
        }
        return mapping;
    }
}
