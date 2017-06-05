package le.data.scs.spider.crawler.parser;


import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

/**
 * Created by yangyong3 on 2017/2/16.
 * html数据解析接口
 */
public interface Parser<DATA,META> {
    /**
     * 解析抓取完成的html or json 转换成相应的对象数据
     * @param crawlerEntity
     * @return
     * @throws ParserException
     */
    public ParserEntity<DATA> parser(CrawlerEntity<META> crawlerEntity) throws ParserException;
}
