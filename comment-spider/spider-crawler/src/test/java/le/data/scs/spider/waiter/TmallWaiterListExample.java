package le.data.scs.spider.waiter;

import le.data.scs.spider.http.HttpWalker;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.http.support.HttpClientWalker;
import le.data.scs.spider.http.support.JsoupWalker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/2/20.
 * 天猫客服人员列表获取
 */
public class TmallWaiterListExample {

    private static final String cookie = "_tb_token_=3e5b9b638e9e1; x=2043230365; uc3=sg2=U7Af3epKKemwJ1DVq%2Fe922foPAJRp5htOaXRyN5ABjg%3D&nk2=&id2=&lg2=; uss=UUsdU7SI%2BnsMwX4KPRjEdWgVEQoSR6JbuJy8KGlXaXdQkL60XmDF7pbS; tracknick=; sn=%E4%B9%90%E8%A7%86%E5%AE%98%E6%96%B9%E6%97%97%E8%88%B0%E5%BA%97%3A%E8%92%99%E5%A4%9A; skt=c3ac1c7dacd12e8d; cna=V786EfA9VWcCAXt9GuaVeHqJ; v=0; cookie2=18b9811bdfc1ce07dc0842bbe536dd39; unb=2612537007; t=6c95b024d9c5f7cf7a80616027a60fb5; uc1=cookie14=UoW%2FVURaIxFFoQ%3D%3D&lng=zh_CN; l=AigoheC-ze3sj6e3MPP8oJfEeBw6XIxb; isg=Ak5OFSv3mgqF6i4ueU45ZKBxnyQyBxLJE2tkF3iXt9EM2-414F9i2fTbZbBN";
    private static HttpWalker httpWalker = new HttpClientWalker();

    public List<CSWaiter> getWaiterList() throws IOException {
        String filePath = "D:\\le_eco\\customer-voice\\le.data.scs\\le.data.scs.spider\\src\\main\\resources\\script\\tmall_cookie.json";
        String url = "https://zizhanghao.taobao.com/subaccount/monitor/chat_record_query.htm";
        List<CSWaiter> list = new ArrayList<CSWaiter>();
        Map<String, String> header = new HashMap<>();
        header.put("Cookie", "uc1=cookie14=UoW%2FVUeSZVvq0A%3D%3D&lng=zh_CN;uc3=sg2=U7Af3epKKemwJ1DVq%2Fe922foPAJRp5htOaXRyN5ABjg%3D&nk2=&id2=&lg2=;tracknick=;unb=2612537007;l=Aqys-qudEHOlisdCjuyw8q9JfZHeZVAP;cookie2=1899bbcb2a7c7536fc3a214e9d7e91b0;t=0591e13fe57fdb14353b9b51d5ce10cc;uss=B0Fh9pZA69Sew8c8wNgLCJSMhXBr9QvCKtOVELjcBPJRJb3zSBCxuTt1;v=0;cna=V/M7Eeq+OxoCAXt9GubRRxC4;x=2043230365;sn=%E4%B9%90%E8%A7%86%E5%AE%98%E6%96%B9%E6%97%97%E8%88%B0%E5%BA%97%3A%E8%92%99%E5%A4%9A;apush09808c396ca81fe43916230bb975b391=%7B%22ts%22%3A1488258379230%2C%22parentId%22%3A1488258376184%7D;isg=Ao6OVcaFWkyRC-5o7X1-GbpB02Ryx1IJN9I-3LjX-hFMGy51IJ-iGTRZgf2I;skt=efb570ea0f3e97c0;_tb_token_=e1b8aeb84b85e;");
        Response response = httpWalker.get(new Request(url, header));
        Document document = Jsoup.parse(response.getContent());
        Element element = document.getElementById("employeeNick");
        Elements waiters = element.select("option");
        Element temp = null;
        if(waiters!=null&&waiters.size()>0){
            for(int i=0;i<waiters.size();i++){
                temp = waiters.get(i);
                list.add(getWaiter(temp));
            }
        }
        return list;
    }

    private CSWaiter getWaiter(Element temp) {
        return new CSWaiter(temp.attr("value"),1);
    }

    public static void main(String[] args) throws IOException {
        TmallWaiterListExample example = new TmallWaiterListExample();
        List<CSWaiter> waiters = example.getWaiterList();
        System.out.println(waiters.size());
    }
}
