package le.data.scs.spider.rest.service;

import le.data.scs.common.entity.meta.chat.ChatLog;
import le.data.scs.common.entity.meta.jd.JDCommentEntity;
import le.data.scs.common.entity.meta.tmall.TMallCommentEntity;
import le.data.scs.spider.crawler.cookie.SpiderCookieFactory;
import le.data.scs.spider.crawler.crawler.common.CrawlerException;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;

import le.data.scs.spider.crawler.parser.ParserException;
import le.data.scs.spider.crawler.persist.support.mysql.PersisterDao;
import le.data.scs.spider.crawler.service.JDSpiderService;
import le.data.scs.spider.crawler.service.TMallSpiderService;
import le.data.scs.spider.distribution.mq.MQException;
import le.data.scs.spider.http.cookie.CookieException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by yangyong3 on 2017/3/13.
 */
@RestController
@RequestMapping("/spider")
public class CrawlerService {

    private JDSpiderService jdSpiderService = new JDSpiderService();

    private TMallSpiderService tMallSpiderService = new TMallSpiderService();

    @RequestMapping(value = "/seed/comment/tmall", method = RequestMethod.POST)
    public void tmallComment(@RequestBody TMallCommentEntity tMallCommentEntity) throws IllegalAccessException, ParserException, CrawlerException, InstantiationException, MQException, ClassNotFoundException, IOException {
        System.out.println(tMallCommentEntity);
        tMallSpiderService.saveCommentSeed(tMallCommentEntity);
    }

    @RequestMapping(value = "/seed/comment/jd", method = RequestMethod.POST)
    public void jdCommentSeed(@RequestBody JDCommentEntity jdCommentEntity) throws IllegalAccessException, ParserException, CrawlerException, InstantiationException, MQException, ClassNotFoundException, IOException {
        System.out.println(jdCommentEntity);
        jdSpiderService.saveCommentSeed(jdCommentEntity);
    }

    @RequestMapping(value = "/cookie/{type}", method = RequestMethod.GET)
    public String getCookie(@PathVariable String type) {
        try {
            return SpiderCookieFactory.getCookie(CrawlerType.valueOf(type.toUpperCase()));
        } catch (CookieException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/detial/chat/{id}", method = RequestMethod.GET)
    public ChatLog getChatLog(@PathVariable String id) {
        return PersisterDao.findChatById(id);
    }

    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    public String listCommentSeed() {
        List<String> seeds = PersisterDao.listCommentSeed();
        StringBuffer stringBuffer = new StringBuffer(1000);
        for (String line : seeds) {
            stringBuffer.append(line + "\n");
        }
        return stringBuffer.toString();
    }
}
