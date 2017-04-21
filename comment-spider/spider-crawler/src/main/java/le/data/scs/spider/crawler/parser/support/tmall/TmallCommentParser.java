package le.data.scs.spider.crawler.parser.support.tmall;

import le.data.scs.common.entity.meta.comment.CommentDetail;
import le.data.scs.common.entity.meta.comment.CommentLog;
import le.data.scs.common.entity.meta.tmall.TMallCommentEntity;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import le.data.scs.spider.crawler.entity.common.PageEntity;
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
public class TmallCommentParser implements Parser<CommentLog,TMallCommentEntity> {
    @Override
    public ParserEntity<CommentLog> parser(CrawlerEntity<TMallCommentEntity> crawlerEntity) throws ParserException {
        String json = crawlerEntity.getBody().trim();
        if(!json.startsWith("{")){
            json = "{"+json+"}";
        }
        ParserEntity<CommentLog> parserEntity = new ParserEntity<CommentLog>();
        CommentLog log = new CommentLog();
        log.setMd5(crawlerEntity.getMeta().getMd5());
        log.setProductId(crawlerEntity.getMeta().getItemId());
        log.setTags(crawlerEntity.getMeta().getTags());
        log.setUrl(crawlerEntity.getMeta().getUrl());
        try {
            List<CommentDetail> details = new ArrayList<CommentDetail>();
            CommentDetail detail = null;
            Map<String,Object> temp = JsonUtils.fromJson(json,Map.class);
            Map<String,Object> mapJson = null;
            if(temp.containsKey("rateDetail")){
                mapJson = (Map<String, Object>) temp.get("rateDetail");
            }else{
                parserEntity.setData(log);
                return parserEntity;
            }
            Map<String,Object> paginator = null;
            List<Map<String,Object>> rateList = null;
            if(mapJson.containsKey("paginator")){
                paginator = (Map<String, Object>) mapJson.get("paginator");
                PageEntity page = new PageEntity();
                page.setPage((Integer) paginator.get("page"));
                page.setTotalNum((Integer) paginator.get("items"));
                page.setTotalPage((Integer) paginator.get("lastPage"));
                parserEntity.setPage(page);
            }
            if(mapJson.containsKey("rateList")){
                rateList = (List<Map<String, Object>>) mapJson.get("rateList");
                for(Map<String,Object> rate:rateList){
                    detail = new CommentDetail();
                    detail.setCreateTime(rate.get("rateDate").toString());
                    detail.setText(rate.get("rateContent").toString());
                    details.add(detail);
                }
                log.setDetails(details);
                parserEntity.setData(log);
            }
        } catch (IOException e) {
            throw new ParserException(e.getMessage(),e);
        }
        return parserEntity;
    }
}
