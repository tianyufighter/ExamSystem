package com.tianyufighter.dao;

import com.tianyufighter.model.User;

/**
 * 操作用户的数据的接口
 */
public interface UserDao {
    int insert(User user);
    int delete(int id);
    int update(User user);
    User findUser(String examineeNumber, String password);
    void deleteTable();
}
