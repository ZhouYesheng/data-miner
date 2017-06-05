package le.data.scs.common.jdbc;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by yangyong3 on 2017/3/15.
 * 数据库配置文件对应的读取类，用来读取db.properties配置文件
 */
public class DBConfig {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("db", Locale.getDefault());

    public static final String db_driver = bundle.getString("spider.db.driver");

    public static final String db_url = bundle.getString("spider.db.url");

    public static final String db_user = bundle.getString("spider.db.username");

    public static final String db_pass = bundle.getString("spider.db.password");
}
