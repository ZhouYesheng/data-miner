package le.data.scs.spider.crawler.crawler.common;



import le.data.scs.spider.crawler.entity.common.CrawlerEntity;

/**
 * Created by yangyong3 on 2017/2/16.
 * 网页抓取接口
 */
public interface Crawler<Meta> {
    /**
     * get请求获取网页内容
     * @param meta
     * @return
     * @throws CrawlerException
     */
    public CrawlerEntity crawlByGet(Meta meta) throws CrawlerException;

    /**
     * http post方式获取网页内容
     * @param meta
     * @return
     * @throws CrawlerException
     */
    public CrawlerEntity crawlByPost(Meta meta) throws CrawlerException;
}
