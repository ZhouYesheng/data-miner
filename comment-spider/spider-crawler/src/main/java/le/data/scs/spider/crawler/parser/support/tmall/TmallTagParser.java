package le.data.scs.spider.crawler.parser.support.tmall;

import le.data.scs.common.entity.meta.tmall.TMallCommentEntity;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.ParserEntity;
import le.data.scs.spider.crawler.parser.Parser;
import le.data.scs.spider.crawler.parser.ParserException;
import le.data.scs.spider.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/3/9.
 */
public class TmallTagParser implements Parser<List<String>, TMallCommentEntity> {
    @Override
    public ParserEntity<List<String>> parser(CrawlerEntity<TMallCommentEntity> crawlerEntity) throws ParserException {
        String json = crawlerEntity.getBody().trim();
        if(!json.startsWith("{")){
            json = "{"+json+"}";
        }
        List<String> tags = new ArrayList<String>();
        ParserEntity<List<String>> parserEntity = new ParserEntity<List<String>>();
        try {
            Map<String, Object> temp = JsonUtils.fromJson(json, Map.class);
            Map<String,Object> mapJson = null;
            if(temp.containsKey("tags")){
                mapJson = (Map<String, Object>) temp.get("tags");
            }else{
                parserEntity.setData(tags);
                return parserEntity;
            }
            if (mapJson.containsKey("tagClouds")) {
                List<Map<String, Object>> tagClouds = (List<Map<String, Object>>) mapJson.get("tagClouds");
                for (Map<String, Object> map : tagClouds) {
                    tags.add(map.get("tag").toString());
                }
            }
        } catch (IOException e) {
            throw new ParserException(e.getMessage(), e);
        }
        parserEntity.setData(tags);
        return parserEntity;
    }
}
