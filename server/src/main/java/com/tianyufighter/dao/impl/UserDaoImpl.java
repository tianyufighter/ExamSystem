package com.tianyufighter.dao.impl;

import com.tianyufighter.dao.UserDao;
import com.tianyufighter.model.User;
import com.tianyufighter.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private Connection conn = null;
    private PreparedStatement stmt = null;

    /**
     * 将用户的数据插入表中
     * @param user
     * @return
     */
    @Override
    public int insert(User user) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO user(username, identity, examinee_number, password) VALUES(?, ?, ?, ?);";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getIdentity());
            stmt.setString(3, user.getExamineeNumber());
            stmt.setString(4, user.getPassword());
            int res = stmt.executeUpdate();
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据id删除删除用户
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "DELETE FROM user WHERE id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int res = stmt.executeUpdate();
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * 更新用户的数据
     * @param user
     * @return
     */
    @Override
    public int update(User user) {
        try {
            conn = JDBCUtils.getConnection();
//            String sql = "UPDATE user SET username = ?, identity = ?, examinee_number = ?, password = ? WHERE id = ?;";
            String sql = "UPDATE user SET islogin = ?, score = ? WHERE examinee_number = ? AND password = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, user.getIslogin());
            stmt.setInt(2, user.getScore());
            stmt.setString(3, user.getExamineeNumber());
            stmt.setString(4,user.getPassword());
            int res = stmt.executeUpdate();
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据用户名和密码查询用户
     * @param examineeNumber 准考证号
     * @param password 密码
     * @return 返回null表示没有该用户，反之则有
     */
    @Override
    public User findUser(String examineeNumber, String password) {
        ResultSet res = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "SELECT * FROM user WHERE examinee_number = ? AND password = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, examineeNumber);
            stmt.setString(2, password);
            res = stmt.executeQuery();
            while(res.next()) {
                return new User(res.getInt("id"), res.getString("username"), res.getString("identity"), res.getString("examinee_number"), res.getString("password"), res.getBoolean("islogin"), res.getInt("score"), false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 删除数据库中user表的所有数据
     */
    @Override
    public void deleteTable() {
        try {
            conn = JDBCUtils.getConnection();
            String sql = "delete from user";
            stmt = conn.prepareStatement(sql);
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
