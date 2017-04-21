package le.data.scs.spider.crawler.crawler.support.jd;

import le.data.scs.common.entity.meta.jd.JDSeedEntity;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.crawler.crawler.common.CrawlerAdapter;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/23.
 */
public class JDChatCrawler extends CrawlerAdapter<JDSeedEntity> {

    private static final Logger log = LoggerFactory.getLogger(JDChatCrawler.class);

    private static final String baseUrl = "http://kf.jd.com/chatLog/queryList.action?";

    public JDChatCrawler(CrawlerType type) {
        super(type);
    }

    public JDChatCrawler(){
        this(CrawlerType.JD);
    }

    @Override
    public Request createRuquest(JDSeedEntity jdSeedEntity) throws CrawlerException {
        String url = getUrl(jdSeedEntity);
        String cookie = findCookie(this.type);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Cookie", cookie);
        return new Request(url, header);
    }

    @Override
    public boolean check(Request request, Response response, JDSeedEntity jdSeedEntity) throws CrawlerException {
         return baseCheck(request,response,jdSeedEntity);
    }

    private String getUrl(JDSeedEntity jdSeedEntity) {
        StringBuilder url = new StringBuilder(100);
        url.append(baseUrl);
        url.append("page=" + jdSeedEntity.getPage() + "&");
        url.append("pageSize=" + jdSeedEntity.getPageSize() + "&");
        url.append("startTime=" + jdSeedEntity.getStartTime() + "&");
        url.append("endTime=" + jdSeedEntity.getEndTime() + "&");
        url.append("productId=" + jdSeedEntity.getProductId() + "&");
        url.append("orderId=" + jdSeedEntity.getOrderId() + "&");
        url.append("customer=" + jdSeedEntity.getCustomer() + "&");
        url.append("servicePin=" + jdSeedEntity.getServicePin() + "&");
        url.append("sessionType=" + jdSeedEntity.getSessionType() + "&");
        url.append("sessionStatus=" + jdSeedEntity.getSessionStatus() + "&");
        url.append("keyWord=" + jdSeedEntity.getKeyWord());
        log.info("get url ok url is -["+url.toString()+"]");
        return url.toString();
    }
}
