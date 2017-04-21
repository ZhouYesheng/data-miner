package le.data.scs.spider.crawler.persist;

import le.data.scs.spider.crawler.entity.common.ParserEntity;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public interface Persister<DATA, META> {
    public void persist(ParserEntity<DATA> parserEntity, META meta) throws PersisterException;
}
