package le.data.scs.spider.config;

import le.data.scs.spider.config.common.SpiderConfig;
import le.data.scs.spider.config.support.SpiderConfigException;

/**
 * Created by yangyong3 on 2017/2/22.
 */
public interface ConfigStorage {
    public SpiderConfig getSpiderConfig() throws SpiderConfigException;
}
