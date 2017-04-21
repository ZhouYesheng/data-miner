package le.data.scs.spider.crawler.parser.support.tmall;

import le.data.scs.common.entity.meta.tmall.TMallCustomerEntity;
import le.data.scs.common.entity.meta.tmall.TMallSeedEntity;
import le.data.scs.common.entity.meta.tmall.TMallSeedQueryEntity;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserException;
import le.data.scs.spider.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TmallSeedParser extends TmallBaseParser implements Parser<List<TMallSeedEntity>, TMallCustomerEntity> {
    @Override
    public ParserEntity<List<TMallSeedEntity>> parser(CrawlerEntity<TMallCustomerEntity> crawlerEntity) throws ParserException {
        String json = crawlerEntity.getBody();
        ParserEntity parserEntity = new ParserEntity();
        TMallCustomerEntity waiter = crawlerEntity.getMeta();
        List<TMallSeedEntity> seeds = new ArrayList<TMallSeedEntity>();
        try {
            Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
            List<String> customers = null;
            List<String> waiters = null;
            if (map.containsKey("customerUserNicks")) {
                customers = (List<String>) map.get("customerUserNicks");
                waiters = (List<String>) map.get("employeeUserNicks");
                for (String ww : waiters) {
                    for (String customer : customers) {
                        if (!StringUtils.isBlank(customer))
                            seeds.add(new TMallSeedEntity("", "", new TMallSeedQueryEntity(new String[]{ww}, new String[]{customer}, waiter.getQueryEntity().getStart(), waiter.getQueryEntity().getEnd())));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        parserEntity.setData(seeds);
        return parserEntity;
    }
}
