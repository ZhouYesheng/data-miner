package com.young.spider.config.support;


import com.young.spider.config.ConfigStorage;
import com.young.spider.config.common.SpiderConfig;
import com.young.spider.zkclient.ZKClientFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yangyong3 on 2017/2/22.
 */
public class ZKConfigStorage extends AbstractStorage implements ConfigStorage {

    private static final Logger log = LoggerFactory.getLogger(ZKConfigStorage.class);

    public ZKConfigStorage(String configFilePath) {
        super(configFilePath);
    }

    @Override
    public SpiderConfig getSpiderConfig() throws SpiderConfigException {
        String configData = null;
        SpiderConfig spiderConfig = null;
        try {
            byte[] bytes = ZKClientFactory.getZKClient().getData().forPath(configFilePath);
            log.info("read config xml from zk path is -"+configFilePath+"]");
            if (bytes != null)
                configData = new String(bytes, "utf-8");
            if (!StringUtils.isBlank(configData)) {
                spiderConfig = xml.fromXml(configData, SpiderConfig.class);
            }
        } catch (Exception e) {
            throw new SpiderConfigException("read zk path [" + configFilePath + "] error ", e);
        }
        return spiderConfig;
    }
}
