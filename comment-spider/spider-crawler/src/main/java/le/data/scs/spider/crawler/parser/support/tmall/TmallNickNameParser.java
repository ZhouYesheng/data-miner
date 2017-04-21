package le.data.scs.spider.crawler.parser.support.tmall;

import le.data.scs.common.entity.meta.tmall.TMallCustomerEntity;
import le.data.scs.common.entity.meta.tmall.TMallCustomerQueryEntity;
import le.data.scs.common.entity.meta.tmall.TMallNickEntity;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;

import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TmallNickNameParser extends TmallBaseParser implements Parser<List<TMallCustomerEntity>,TMallNickEntity> {
    @Override
    public ParserEntity<List<TMallCustomerEntity>> parser(CrawlerEntity<TMallNickEntity> crawlerEntity) throws ParserException {
        ParserEntity<List<TMallCustomerEntity>> parserEntity = new ParserEntity<List<TMallCustomerEntity>>();
        List<TMallCustomerEntity> nicknames = new ArrayList<TMallCustomerEntity>();
        Document document = Jsoup.parse(crawlerEntity.getBody());
        Element element = document.getElementById("employeeNick");
        Elements waiters = element.select("option");
        Element temp = null;
        if(waiters!=null&&waiters.size()>1){
            for(int i=2;i<waiters.size();i++){
                temp = waiters.get(i);
                nicknames.add(getTMallSeedEntity(temp,crawlerEntity.getMeta()));
            }
        }
        parserEntity.setData(nicknames);
        return parserEntity;
    }
    private TMallCustomerEntity getTMallSeedEntity(Element element,TMallNickEntity tMallNickEntity){
        TMallCustomerQueryEntity query = new TMallCustomerQueryEntity(element.attr("value"),"",tMallNickEntity.getStart(),tMallNickEntity.getEnd());
        query.setCustomerAll(true);
        return new TMallCustomerEntity(null,null,query);
    }
}
