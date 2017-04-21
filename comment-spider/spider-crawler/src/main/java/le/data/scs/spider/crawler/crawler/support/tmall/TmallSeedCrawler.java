package le.data.scs.spider.crawler.crawler.support.tmall;

import le.data.scs.common.entity.meta.tmall.TMallCustomerEntity;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import le.data.scs.spider.utils.JsonUtils;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by yangyong3 on 2017/2/27.
 */
public class TmallSeedCrawler extends TmallCrawlerAdapter<TMallCustomerEntity> {

    private static final String baseUrl = "https://zizhanghao.taobao.com/subaccount/monitor/chatRecordJson.htm?action=/subaccount/monitor/ChatRecordQueryAction&eventSubmitDoQueryChatRealtion=anything";

    private static final String params = "chatRelationQuery";

    public TmallSeedCrawler(CrawlerType type) {
        super(type);
    }

    public TmallSeedCrawler() {
        this(CrawlerType.TMALL);
    }

    @Override
    public Request createRuquest(TMallCustomerEntity tMallSeedEntity) throws CrawlerException {
        try {
            return baseReqeust(baseUrl,params, URLEncoder.encode(JsonUtils.toJson(tMallSeedEntity.getQueryEntity()),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean check(Request request, Response response, TMallCustomerEntity s) throws CrawlerException {
       return baseCheck(request,response,s);
    }
}
