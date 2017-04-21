package le.data.scs.spider.crawler.crawler.common;

import le.data.scs.spider.crawler.http.HttpWalkerFactory;
import le.data.scs.spider.http.HttpWalker;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.http.cookie.CookieException;

import le.data.scs.spider.crawler.cookie.SpiderCookieFactory;
import le.data.scs.spider.crawler.entity.common.CrawlerEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by yangyong3 on 2017/2/23.
 * 抓取适配器
 */
public abstract class CrawlerAdapter<Meta> implements Crawler<Meta> {

    protected CrawlerType type;

    public CrawlerAdapter(CrawlerType type) {
        this.type = type;
    }

    private static final Logger log = LoggerFactory.getLogger(CrawlerAdapter.class);

    /**
     * 根据Meta信息组装Request对象
     * @param meta
     * @return
     */
    public abstract Request createRuquest(Meta meta) throws CrawlerException;
    /**
     * 检查Response的有效性
     * @param request
     * @param response
     * @param meta
     * @return
     * @throws CrawlerException
     */
    public abstract boolean check(Request request, Response response, Meta meta) throws CrawlerException;

    @Override
    public CrawlerEntity crawlByGet(Meta meta) throws CrawlerException {
        Request request = createRuquest(meta);
        HttpWalker walker = HttpWalkerFactory.getHttpWalker(type);
        Response response = null;
        try {
            long start = System.currentTimeMillis();
            log.info("get request -["+request+"]");
            response = walker.get(request);
            log.info("get request -["+request+"] ok cost time -[ "+(System.currentTimeMillis()-start)+" ]response is ["+response+"]");
            return processResponse(request, response, meta);
        } catch (IOException e) {
            throw new CrawlerException("get request -[" + request + "] error response is [" + response + "]", e);
        }
    }

    private CrawlerEntity processResponse(Request request, Response response, Meta meta) throws CrawlerException {
        check(request, response, meta);
        CrawlerEntity entity = new CrawlerEntity();
        entity.setBody(response.getContent());
        entity.setMeta(meta);
        return entity;
    }

    @Override
    public CrawlerEntity crawlByPost(Meta meta) throws CrawlerException {
        Request request = createRuquest(meta);
        HttpWalker walker = HttpWalkerFactory.getHttpWalker(type);
        Response response = null;
        try {
            long start = System.currentTimeMillis();
            log.info("get request -["+request+"]");
            response = walker.post(request);
            log.info("get request -["+request+"] ok cost time -[ "+(System.currentTimeMillis()-start)+" ]response is ["+response+"]");
            return processResponse(request, response, meta);
        } catch (IOException e) {
            throw new CrawlerException("get request -[" + request + "] error response is [" + response + "]", e);
        }
    }

    protected String findCookie(CrawlerType type) throws CrawlerException {
        String cookie = null;
        try {
            cookie = SpiderCookieFactory.getCookie(type);
            if(StringUtils.isBlank(cookie))
                throw new CrawlerException("cookie is null cookie is ["+cookie+"]");
        }  catch (CookieException e) {
            throw new CrawlerException("cookie error cookie is ["+cookie+"]",e);
        }
        return cookie;
    }

    protected boolean baseCheck(Request request, Response response, Meta meta) throws CrawlerException {
        //这里可以对response进行判断，比如statusCode！=200或者其他错误
        if(response.getContent().trim().startsWith("<!DOCTYPE html>")) {
            try {
                SpiderCookieFactory.delCookie(this.type);
            } catch (CookieException e) {
                throw new CrawlerException("delete cookie error",e);
            }
            throw new CrawlerException("Cookie expire", new CookieException("cookie expired"));
        }
        return (response.getStatusCode() == 200);
    }
}
