package com.tianyufighter.actListener;

import com.tianyufighter.dao.UserDao;
import com.tianyufighter.dao.impl.UserDaoImpl;
import com.tianyufighter.draw.HomePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreListener implements ActionListener {
    private HomePage homePage;

    public StoreListener(HomePage homePage) {
        this.homePage = homePage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UserDao userDao = new UserDaoImpl();
        for(int i = 0; i < homePage.user.size(); i++) {
            userDao.update(homePage.user.get(i));
        }
        System.out.println("学生成绩成功导入数据库");
        JOptionPane.showMessageDialog(homePage.frame, "学生成绩已经成功导入数据库", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
    }
}
