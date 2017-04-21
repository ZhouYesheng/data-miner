package le.data.scs.spider.crawler.parser.support.jd;

import le.data.scs.common.entity.meta.comment.CommentDetail;
import le.data.scs.common.entity.meta.comment.CommentLog;
import le.data.scs.common.entity.meta.jd.JDCommentEntity;
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
 * Created by yangyong3 on 2017/3/8.
 */
public class JDCommentsParser implements Parser<CommentLog, JDCommentEntity> {

    @Override
    public ParserEntity<CommentLog> parser(CrawlerEntity<JDCommentEntity> crawlerEntity) throws ParserException {
        String json = crawlerEntity.getBody();
        ParserEntity<CommentLog> parserEntity = new ParserEntity<CommentLog>();
        CommentLog log = new CommentLog();
        log.setProductId(crawlerEntity.getMeta().getProductId());
        log.setMd5(crawlerEntity.getMeta().getMd5());
        log.setUrl(crawlerEntity.getMeta().getPageUrl());
        Map<String, Object> mapData = null;
        List<Map<String, Object>> hotCommentTagStatistics = null;
        List<Map<String, Object>> comments = null;
        CommentDetail detail = null;
        List<CommentDetail> details = new ArrayList<CommentDetail>();
        Map<String,Object> summary = null;
        try {
            mapData = JsonUtils.fromJson(json, Map.class);
            if(mapData.containsKey("productCommentSummary")){
                summary = (Map<String, Object>) mapData.get("productCommentSummary");
                PageEntity page = new PageEntity();
                int allCount = (int) summary.get(crawlerEntity.getMeta().getCountColumn());
                page.setTotalNum(allCount);
                page.setPage(crawlerEntity.getMeta().getPage());
                page.setTotalPage(allCount%crawlerEntity.getMeta().getPageSize()==0?allCount/crawlerEntity.getMeta().getPageSize():(allCount/crawlerEntity.getMeta().getPageSize())+1);
                parserEntity.setPage(page);
            }
            if (mapData.containsKey("hotCommentTagStatistics")) {
                List<String> tags = new ArrayList<String>();
                hotCommentTagStatistics = (List<Map<String, Object>>) mapData.get("hotCommentTagStatistics");
                for (Map<String, Object> map : hotCommentTagStatistics) {
                    tags.add(map.get("name").toString());
                }
                log.setTags(tags);
            }
            if (mapData.containsKey("comments")) {
                comments = (List<Map<String, Object>>) mapData.get("comments");
                for (Map<String, Object> comment : comments) {
                    detail = new CommentDetail();
                    detail.setCreateTime(comment.get("creationTime").toString());
                    detail.setScore((Integer) comment.get("score"));
                    detail.setText(comment.get("content").toString());
                    details.add(detail);
                }
            }
            log.setDetails(details);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parserEntity.setData(log);
        return parserEntity;
    }
}
