package le.data.scs.spider.http.cookie;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/2/27.
 * cookie实体，保存cookie相关的信息
 */
public class CookieEntity implements Serializable {
    private String name;
    private String value;
    private String path;
    private String domain;
    private Long expiry;
    private boolean isSecure;
    private boolean isHttpOnly;

    public CookieEntity(String name, String value, String path, String domain, Long expiry, boolean isSecure, boolean isHttpOnly) {
        this.name = name;
        this.value = value;
        this.path = path;
        this.domain = domain;
        this.expiry = expiry;
        this.isSecure = isSecure;
        this.isHttpOnly = isHttpOnly;
    }

    public CookieEntity(){}

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public String getDomain() {
        return domain;
    }

    public Long getExpiry() {
        return expiry;
    }

    public boolean isSecure() {
        return isSecure;
    }

    public boolean isHttpOnly() {
        return isHttpOnly;
    }
}
