package le.data.scs.spider.crawler.crawler.common;



import le.data.scs.spider.crawler.entity.common.CrawlerEntity;

/**
 * Created by yangyong3 on 2017/2/16.
 */
public interface Crawler<Meta> {
    public CrawlerEntity crawlByGet(Meta meta) throws CrawlerException;

    public CrawlerEntity crawlByPost(Meta meta) throws CrawlerException;
}
