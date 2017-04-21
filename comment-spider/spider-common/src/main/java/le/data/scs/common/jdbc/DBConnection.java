package le.data.scs.common.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by yangyong3 on 2017/3/15.
 */
public class DBConnection {

    private static Connection con;

    static {
        try {
            Class.forName(DBConfig.db_driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static Connection getConnection() throws SQLException {
        if (con == null)
            con = getConnection(DBConfig.db_url, DBConfig.db_user, DBConfig.db_pass);
        return con;
    }

    public synchronized static void close() throws SQLException {
        if(con!=null&&!con.isClosed())
            con.close();
    }

    public static Connection getConnection(String url,String user,String password) throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }
}
