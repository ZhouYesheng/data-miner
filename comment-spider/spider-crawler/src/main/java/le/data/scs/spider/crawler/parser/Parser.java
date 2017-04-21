package le.data.scs.spider.crawler.parser;


import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

/**
 * Created by yangyong3 on 2017/2/16.
 */
public interface Parser<DATA,META> {
    public ParserEntity<DATA> parser(CrawlerEntity<META> crawlerEntity) throws ParserException;
}
