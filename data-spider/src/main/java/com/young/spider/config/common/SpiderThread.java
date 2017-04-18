package com.young.spider.config.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.io.Serializable;

/**
 * Created by yangyong3 on 2017/2/22.
 */
@XStreamAlias("thread")
public class SpiderThread implements Serializable {

    @XStreamAsAttribute
    private String name;
    @XStreamAsAttribute
    private String classname;
    @XStreamAsAttribute
    private boolean use;
    @XStreamAsAttribute
    private Integer size;

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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
