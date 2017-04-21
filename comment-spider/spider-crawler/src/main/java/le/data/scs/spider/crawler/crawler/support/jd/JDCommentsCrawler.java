package le.data.scs.spider.crawler.crawler.support.jd;

import le.data.scs.common.entity.meta.jd.JDCommentEntity;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.crawler.crawler.common.CrawlerAdapter;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;


/**
 * Created by yangyong3 on 2017/3/8.
 */
public class JDCommentsCrawler extends CrawlerAdapter<JDCommentEntity> {

    private static final String baseUrl = "https://club.jd.com/comment/productPageComments.action?";

    public JDCommentsCrawler(CrawlerType type) {
        super(type);
    }

    public JDCommentsCrawler(){
        this(CrawlerType.JD);
    }

    @Override
    public Request createRuquest(JDCommentEntity jdCommentEntity) throws CrawlerException {
        String url = getUrl(baseUrl,jdCommentEntity);
//        String cookie = findCookie(this.type);
//        Map<String, String> header = new HashMap<String, String>();
//        header.put("Cookie", cookie);
        return new Request(url,null,null,"gb2312");
    }

    private String getUrl(String baseUrl, JDCommentEntity jdCommentEntity) {
        StringBuilder url = new StringBuilder();
        url.append(baseUrl);
        url.append("productId="+jdCommentEntity.getProductId()+"&");
        url.append("score="+jdCommentEntity.getScore()+"&");
        url.append("sortType="+jdCommentEntity.getSortType()+"&");
        url.append("page="+jdCommentEntity.getPage()+"&");
        url.append("pageSize="+jdCommentEntity.getPageSize()+"&");
        url.append("isShadowSku="+jdCommentEntity.getIsShadowSku());
        return url.toString();
    }

    @Override
    public boolean check(Request request, Response response, JDCommentEntity jdCommentEntity) throws CrawlerException {
        return baseCheck(request,response,jdCommentEntity);
    }
}
