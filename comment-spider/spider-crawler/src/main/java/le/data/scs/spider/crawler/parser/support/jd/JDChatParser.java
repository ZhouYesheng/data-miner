package le.data.scs.spider.crawler.parser.support.jd;

import le.data.scs.common.entity.meta.jd.JDSeedEntity;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.PageEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserException;
import le.data.scs.spider.utils.JsonUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class JDChatParser implements Parser<String,JDSeedEntity> {
    @Override
    public ParserEntity<String> parser(CrawlerEntity<JDSeedEntity> crawlerEntity) throws ParserException {
        ParserEntity<String> parserEntity = new ParserEntity<String>();
        parserEntity.setData(crawlerEntity.getBody());
        PageEntity pageEntity = new PageEntity();
        try {
            Map<String,Object> map = JsonUtils.fromJson(crawlerEntity.getBody(),Map.class);
            pageEntity.setPage(crawlerEntity.getMeta().getPage());
            pageEntity.setTotalNum(Integer.parseInt(map.get("totalRecordNum").toString()));
            pageEntity.setTotalPage(Integer.parseInt(map.get("totalPage").toString()));
            parserEntity.setPage(pageEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parserEntity;
    }
}
