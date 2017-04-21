package le.data.scs.spider.tubiao;

import le.data.scs.spider.http.HttpWalker;
import le.data.scs.spider.http.Request;
import le.data.scs.spider.http.Response;
import le.data.scs.spider.http.support.HttpClientWalker;
import le.data.scs.spider.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyong3 on 2017/3/8.
 */
public class TubiaoCrawler {

    private HttpWalker walker = new HttpClientWalker();

    public void crawlerTubiao(String id) throws IOException {
        String url = "http://sj.qq.com/myapp/cate/appList.htm?orgame=1&categoryId="+id+"&pageSize=150";
        Request request = new Request(url);
        Response response = walker.get(request);
        Map<String,Object> map = JsonUtils.fromJson(response.getContent(),Map.class);
        List<Map<String,Object>> list = null;
        Response response1 = null;
        File file = null;
        File parent = null;
        if(map.containsKey("obj")){
            list = (List<Map<String, Object>>) map.get("obj");
            for(Map<String,Object> icon:list){
                response1 =  walker.get(new Request(icon.get("iconUrl").toString()));
                file = new File("D:\\data\\icons\\"+icon.get("appName").toString()+".jpg");
                if(file.exists())
                    continue;
                parent = new File(file.getParent());
                if(!parent.exists())
                    parent.mkdirs();
                try {
                    IOUtils.copy(response1.getInputStream(), new FileOutputStream(file));
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("down load jpg to "+file.getPath());
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] ids = new String[]{"118","111","109","105","100","113","116"};
        TubiaoCrawler tubiao = new TubiaoCrawler();
        //购物
        tubiao.crawlerTubiao("102");
        //阅读
//        for(String line:ids) {
//            tubiao.crawlerTubiao(line);
//            Thread.sleep(1000*60*3);
//        }
    }
}
