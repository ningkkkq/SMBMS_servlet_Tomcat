package com.nkq.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

//操作数据库公共类
public class Basedao {
    private static String driver;
    private static String username;
    private static String password;
    private static String url;
    // 类加载时初始化数据库参数
    static {
        Properties properties = new Properties();
        InputStream in = Basedao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        url = properties.getProperty("url");
    }

    // 获取数据库连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
// 查询公共类
    public static ResultSet excute(Connection connection, String sql, Object[] params, PreparedStatement pre,
            ResultSet result) throws SQLException {
        pre = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pre.setObject(i+1, params[i]);
        }
        result = pre.executeQuery();
        System.out.print(result!=null);
        return result;
    }
// 增删改公共类
    public static int excute(Connection connection, String sql, Object[] params, PreparedStatement pre) throws SQLException {
        pre = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pre.setObject(i+1, params[i]);
        }
        int rows = pre.executeUpdate();
        return rows;
    }

    // 关闭链接
    public static boolean closeResource(Connection con, PreparedStatement pre, ResultSet result){
        boolean flag = true;
        if (result!=null) {
            try {
                result.close();
                result = null;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }
        if (con!=null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }
        if (pre!=null) {
            try {
                pre.close();
                pre = null;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
            }
        }
        // 全部关闭成功，返回true
        return flag;
    }
}
