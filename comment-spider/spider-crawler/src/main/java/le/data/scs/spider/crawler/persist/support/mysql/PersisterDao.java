package le.data.scs.spider.crawler.persist.support.mysql;

import le.data.scs.common.entity.meta.comment.CommentDetail;
import le.data.scs.common.entity.meta.comment.CommentLog;
import le.data.scs.common.jdbc.DBConnection;
import le.data.scs.spider.crawler.crawler.common.CrawlerType;
import org.apache.commons.collections.CollectionUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangyong3 on 2017/3/15.
 * 数据存储工具类
 */
public class PersisterDao {

    private static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 保存抓取完成的评论数据
     * @param log
     * @param type
     */
    public static void saveComments(CommentLog log, CrawlerType type) {
        if (CollectionUtils.isEmpty(log.getDetails()))
            return;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            StringBuilder sql = new StringBuilder(100);
            sql.append("insert into scs_comment_log(md5,productId,tags,create_time,score,content,insert_time,url,`type`,create_timestamp) values(?,?,?,?,?,?,?,?,?,?)");
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql.toString());
            String md5 = log.getMd5();
            String pid = log.getProductId();
            String tags = CollectionUtils.isEmpty(log.getTags()) ? null : log.getTags().toString();
            String url = log.getUrl();
            for (CommentDetail detail : log.getDetails()) {
                ps.setString(1, md5);
                ps.setString(2, pid);
                ps.setString(3, tags);
                ps.setString(4, detail.getCreateTime());
                ps.setInt(5, detail.getScore() == null ? 0 : detail.getScore());
                ps.setString(6, detail.getText());
                ps.setString(7, format.format(new Date()));
                ps.setString(8, url);
                ps.setString(9, type.toString());
                ps.setLong(10, format.parse(detail.getCreateTime()).getTime());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ps, null);
        }
    }

    /**
     * 查看评论是否存在
     * @param md5
     * @return
     */
    public static boolean existCommentSeed(String md5){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder(100);
            sql.append("select id from scs_comment_seed where md5 = '" +md5+ "'");
            con = DBConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql.toString());
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(stmt, rs);
        }
        return false;
    }


    public static void saveCommentSeed(String md5,String url,String jsonData,String type){
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            StringBuilder sql = new StringBuilder(100);
            sql.append("insert into scs_comment_seed(md5,url,body,status,`type`) values (?,?,?,0,?)");
            con = DBConnection.getConnection();
            stmt = con.prepareStatement(sql.toString());
            stmt.setString(1,md5);
            stmt.setString(2,url);
            stmt.setString(3,jsonData);
            stmt.setString(4,type);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(stmt, null);
        }
    }

    public static void updateCommentSeed(String md5,int status){
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            StringBuilder sql = new StringBuilder(100);
            sql.append("update scs_comment_seed set status = ? where md5=?");
            con = DBConnection.getConnection();
            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1,status);
            stmt.setString(2,md5);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(stmt, null);
        }
    }

    public static List<String> listSeedByType(String type){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<String> seeds = new ArrayList<String>();
        try {
            StringBuilder sql = new StringBuilder(100);
            sql.append("select * from scs_comment_seed where `type` = '"+type+"' and status = 0");
            con = DBConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql.toString());
            while (rs.next()) {
              seeds.add(rs.getString("body"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return seeds;
        } finally {
            close(stmt, rs);
        }
        return seeds;
    }

    private static void close(Statement stmt, ResultSet rs) {
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if (stmt != null)
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
