package le.data.scs.spider.config;

import le.data.scs.spider.config.common.SpiderConfig;
import le.data.scs.spider.config.support.SpiderConfigException;

/**
 * Created by yangyong3 on 2017/2/22.
 *配置文件读取接口
 */
public interface ConfigStorage {
    /**
     * 将xml配置文件解析成SpiderConfig对象。
     * @return
     * @throws SpiderConfigException
     */
    public SpiderConfig getSpiderConfig() throws SpiderConfigException;
}
