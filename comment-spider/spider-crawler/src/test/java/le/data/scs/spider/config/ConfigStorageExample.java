package le.data.scs.spider.config;

import le.data.scs.spider.config.support.LocalFileConfigStorage;
import le.data.scs.spider.config.support.SpiderConfigException;

/**
 * Created by yangyong3 on 2017/2/22.
 */
public class ConfigStorageExample {

    public void getStorageFromLocal() throws SpiderConfigException {
        String localFile = "D:\\le_eco\\customer-voice\\le.data.scs\\le.data.scs.spider\\src\\main\\resources\\spider-config.xml";
        ConfigStorage storage = new LocalFileConfigStorage(localFile);
        System.out.println(storage.getSpiderConfig().getMessageQueue().getClassname());
    }

    public static void main(String[] args) throws SpiderConfigException {
        ConfigStorageExample example = new ConfigStorageExample();
        example.getStorageFromLocal();
    }
}
