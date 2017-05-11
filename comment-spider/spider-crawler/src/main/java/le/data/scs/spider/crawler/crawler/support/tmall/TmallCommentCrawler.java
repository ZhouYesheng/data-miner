package le.data.scs.spider.crawler.crawler.support.tmall;

import le.data.scs.common.entity.meta.tmall.TMallCommentEntity;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.crawler.crawler.common.CrawlerAdapter;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;

import java.util.Objects;

/**
 * Created by yangyong3 on 2017/3/9.
 * 天猫评论抓取器
 */
public class TmallCommentCrawler extends CrawlerAdapter<TMallCommentEntity> {

    private static final String COMMENT_CIYUN_BASE_URL = "https://rate.tmall.com/listTagClouds.htm?";

    private static final String COMMENT_DETAIL_BASE_URL = "https://rate.tmall.com/list_detail_rate.htm?";

    public TmallCommentCrawler(CrawlerType type) {
        super(type);
    }

    public TmallCommentCrawler() {
        this(CrawlerType.TMALL);
    }

    @Override
    public Request createRuquest(TMallCommentEntity tMallCommentEntity) throws CrawlerException {
        String url = getUrl(tMallCommentEntity);
        return new Request(url,null,null,"gb2312");
    }

    private String getUrl(TMallCommentEntity tMallCommentEntity) {
        StringBuilder url = new StringBuilder();
        if (Objects.equals(TMallCommentEntity.TMallCommentType.COMMENT_CIYUN.toString(), tMallCommentEntity.getType())) {
            url.append(COMMENT_CIYUN_BASE_URL);
            url.append("isAll=" + tMallCommentEntity.getAll());
            url.append("&isInner=" + tMallCommentEntity.getInner());
            url.append("&t=" + System.currentTimeMillis());
        } else {
            url.append(COMMENT_DETAIL_BASE_URL);
            url.append("tbpm=" + tMallCommentEntity.getTbpm());
            url.append("&spuId=" + tMallCommentEntity.getSpuId());
            url.append("&sellerId=" + tMallCommentEntity.getSellerId());
            url.append("&order=" + tMallCommentEntity.getOrder());
            url.append("&currentPage=" + tMallCommentEntity.getCurrentPage());
            url.append("&append=" + tMallCommentEntity.getAppend());
            url.append("&content=" + tMallCommentEntity.getContent());
        }
        url.append("&itemId=" + tMallCommentEntity.getItemId());
        return url.toString();
    }


    @Override
    public boolean check(Request request, Response response, TMallCommentEntity tMallCommentEntity) throws CrawlerException {
        return baseCheck(request,response,tMallCommentEntity);
    }
}
