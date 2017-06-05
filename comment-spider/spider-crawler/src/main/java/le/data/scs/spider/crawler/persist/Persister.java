package le.data.scs.spider.crawler.persist;

import le.data.scs.spider.crawler.entity.common.ParserEntity;

/**
 * Created by yangyong3 on 2017/2/23.
 * 数据存储接口
 */
public interface Persister<DATA, META> {
    /**
     * 将解析完成的数据进行存储
     * @param parserEntity
     * @param meta
     * @throws PersisterException
     */
    public void persist(ParserEntity<DATA> parserEntity, META meta) throws PersisterException;
}
