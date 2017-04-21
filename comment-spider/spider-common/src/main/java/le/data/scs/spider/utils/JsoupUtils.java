package le.data.scs.spider.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by yangyong3 on 2017/3/3.
 */
public class JsoupUtils {
    public static Document getDocument(String html){
        return Jsoup.parse(html);
    }

    public static Element selectFirstElement(Element element, String css) {
        Elements elements = element.select(css);
        if (elements != null && elements.size() > 0) {
            return elements.first();
        }
        return null;
    }

    public static Elements selectElements(Element element, String css) {
        return element.select(css);
    }

    public static Element getFirstElementByAttribute(Element element,String key,String value){
        Elements elements = element.getElementsByAttributeValue(key,value);
        if (elements != null && elements.size() > 0) {
            return elements.first();
        }
        return null;
    }
}
