package com.tianyufighter.util;

import com.tianyufighter.model.User;

/**
 * 存储当前登录的用户的对象
 */
public class StoreCurrentUser {
    public User user = null;

    public StoreCurrentUser(User user) {
        this.user = user;
    }
}
