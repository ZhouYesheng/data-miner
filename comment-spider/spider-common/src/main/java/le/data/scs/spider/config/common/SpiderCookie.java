package le.data.scs.spider.config.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * Created by yangyong3 on 2017/2/22.
 * 对应xml配置文件中的cookies标签
 */
@XStreamAlias("cookies")
public class SpiderCookie {

    @XStreamImplicit(itemFieldName = "login")
    private List<SpiderCookieLogin> logins;
    @XStreamAsAttribute
    private String storage;

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public List<SpiderCookieLogin> getLogins() {
        return logins;
    }

    public void setLogins(List<SpiderCookieLogin> logins) {
        this.logins = logins;
    }
}
