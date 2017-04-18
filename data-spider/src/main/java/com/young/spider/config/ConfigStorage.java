package com.young.spider.config;


import com.young.spider.config.common.SpiderConfig;
import com.young.spider.config.support.SpiderConfigException;

/**
 * Created by yangyong3 on 2017/2/22.
 */
public interface ConfigStorage {
    public SpiderConfig getSpiderConfig() throws SpiderConfigException;
}
