package le.data.scs.spider.config.support;

import le.data.scs.spider.config.ConfigStorage;
import le.data.scs.spider.config.common.SpiderConfig;
import le.data.scs.spider.zkclient.ZKClientFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by yangyong3 on 2017/2/22.
 *从zookeeper中读取xml配置文件，采用分布式的方式多个实例可以共享一份配置文件
 */
public class ZKConfigStorage extends AbstractStorage implements ConfigStorage {

    private static final Logger log = LogManager.getLogger(ZKConfigStorage.class);

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
