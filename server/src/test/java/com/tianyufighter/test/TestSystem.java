package com.tianyufighter.test;

import com.tianyufighter.dao.UserDao;
import com.tianyufighter.dao.impl.UserDaoImpl;
import com.tianyufighter.model.User;
import com.tianyufighter.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestSystem {
    /**
     * JDBCUtils类的测试
     */
    @Test
    public void test01() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into user values(null, ?, ?, ?, ?, null)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "134");
            pstmt.setString(2, "234");
            pstmt.setString(3, "kejdfk");
            pstmt.setString(4, "24143");
            int count = pstmt.executeUpdate();
            System.out.println(count);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.close(pstmt, conn);
        }
    }

    /**
     * 数据库查找测试
     */
    @Test
    public void test02() {
        UserDao userDao = new UserDaoImpl();
        User user = userDao.findUser("5120194006", "5120194006");
        if(user != null) {
            System.out.println("姓名: " + user.getUsername());
            System.out.println("身份证号: " + user.getIdentity());
            System.out.println("准考证号: " + user.getExamineeNumber());
            System.out.println("密码: " + user.getPassword());
            System.out.println("是否登录: " + user.getIslogin());
            System.out.println("分数: " + user.getScore());
        }
    }
    /**
     * 数据库的增加数据测试
     */
    @Test
    public void test03() {
        User user = new User(null, "wangwu", "5120194009", "123456", "30943", false, 0, false);
        UserDao userDao = new UserDaoImpl();
        int res = userDao.insert(user);
        if(res != 0) {
            System.out.println("插入成功！！！");
        } else {
            System.out.println("插入失败！！！");
        }
    }

    /**
     * 数据库的改数据测试
     */
    @Test
    public void test04() {
        User user = new User(null, "zhangsan", "123erer", "123456", "30943", true, 0, false);
        UserDao userDao = new UserDaoImpl();
        int res = userDao.update(user);
        if(res != 0) {
            System.out.println("更改成功！！！");
        } else {
            System.out.println("更改失败！！！");
        }
    }

    /**
     * 数据库的删除
     */
    @Test
    public void test05() {
        UserDao userDao = new UserDaoImpl();
        int res = userDao.delete(52);
        if(res != 0) {
            System.out.println("删除成功！！！");
        } else {
            System.out.println("删除失败！！！");
        }
    }

}
