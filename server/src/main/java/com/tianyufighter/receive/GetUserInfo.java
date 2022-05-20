package com.tianyufighter.receive;

import com.tianyufighter.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 从文件中获取用户信息
 */
public class GetUserInfo {
    public List<User> user = new ArrayList<User>();
    public List<User> getUser() {
        return user;
    }

    public void setUser(List user) {
        this.user = user;
    }

    /**
     * 根据文件路径获取用户信息
     * @param file 文件路径
     */
    public void getUser(String file) {
        // 临时存储用户的对象的
        User tempUser = null;
        BufferedReader in = null;
        String line = "";
        int index =0;
        try {
            // 创建指定的文件路径的输入流
            in = new BufferedReader(new FileReader(file));
            while((line = in.readLine()) != null) {
                String[] res = line.split("\\|");
                String username = res[1];
                String identity = res[3];
                String examineeNumber = res[5];
                String password = res[3].substring(res[3].length() - 6);
                tempUser = new User(null, username, identity, examineeNumber, password, false, 0, false);
                user.add(tempUser);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
