package le.data.scs.spider.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by yangyong3 on 2017/3/1.
 */
public class MD5 {
    public static String md5(String data){
        return DigestUtils.md5Hex(data);
    }

    public static void main(String[] args){
        System.out.println(md5("398901650@qq.com"));
    }
}
