package com.tianyufighter.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC基于Druid连接池的工具类
 */
public class JDBCUtils {
    // 定义连接池的对象
    private static DataSource ds;
    static {
        try {
            // 加载配置文件
            Properties pro = new Properties();
            pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            // 使用DruidDataSourceFactory工厂类获取DataSource
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    /**
     * 从连接池里获取一个连接对象
     * @return Connection接口
     * @throws SQLException 以后使用的时候需要知道这个方法是否出现异常，用于警告，所以抛出
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 释放资源
     * @param stmt Statement 执行SQL的对象
     * @param conn Connection 数据库连接对象
     */
    public static void close(Statement stmt, Connection conn) {
        // 调用三个参数的close，第一个参数传null
        close(null, stmt, conn);
    }
    /**
     * 释放资源
     * @param rs ResultSet 结果集
     * @param stmt Statement 执行SQL的对象
     * @param conn Connection 数据库连接对象
     */
    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 获取连接对象
     * @return 连接池对象
     */
    public static DataSource getDataSource() {
        return ds;
    }
}
