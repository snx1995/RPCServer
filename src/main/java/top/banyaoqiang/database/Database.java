package top.banyaoqiang.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by 班耀强 on 2018/8/20
 */
public final class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456789";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/byq?" +
            "useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";


    private static Statement stmt;

    static {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            stmt = conn.createStatement();
        } catch (Exception e) {
            logger.error("连接数据库出错 {}", e.getMessage());
        }
    }

    public static ResultSet execute(String sql) {
        try {
            return stmt.executeQuery(sql);
        } catch (Exception e) {
            logger.error("执行查询:{} 时出错: {}", sql, e.getMessage());
        }
        return null;
    }
}
