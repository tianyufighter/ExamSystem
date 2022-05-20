package com.tianyufighter.actListener;

import com.tianyufighter.dao.UserDao;
import com.tianyufighter.dao.impl.UserDaoImpl;
import com.tianyufighter.draw.HomePage;
import com.tianyufighter.model.User;
import com.tianyufighter.receive.GetUserInfo;
import com.tianyufighter.util.MyTableCellRender;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

/**
 * 点击加载考生信息按钮的监听事件
 */
public class ButtonListener implements ActionListener {
    private HomePage homePage = null;
    private MyTableCellRender tcr = new MyTableCellRender();

    public ButtonListener() {
    }

    public ButtonListener(HomePage homePage) {
        this.homePage = homePage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FileDialog fd = new FileDialog(homePage.frame, "Open File", FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getFile() != null) {
            File file = new File(fd.getDirectory() + fd.getFile());
            if (file.exists()) {
                // 返回的总的用户的数量
                int num = readUserInfo(file.toString());
                System.out.println("文件加载成功");
                // 将用户数据插入数据库
//                int res = storeUserInfo(homePage.user);
//                if(res > 0) {
//                    System.out.println("用户信息成功插入数据库");
//                }
                // 将用户数据展示在界面上
                addUserToSurface();
//                System.out.println("开始更新数据了");
                // 每隔两秒更新表中的数据
                updateStatus();
                // 将用户数据存储数据库
                storeUserInfo();
            } else
                System.out.println("文件是无效的！！！！");
        }
        fd.dispose();
    }

    /**
     * 读取文件内容，将用户信息封装成对象
     *
     * @param file 文件的路径名
     * @return 存放用户数组的大小(即用户的数量)
     */
    public int readUserInfo(String file) {
        GetUserInfo getUserInfo = new GetUserInfo();
        getUserInfo.getUser(file);
        homePage.user = getUserInfo.user;
        try {
            return homePage.user.size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * 将考生信息存入数据库
     *
     * @return 存储成功的用户数量
     */
    public int storeUserInfo() {
        UserDao userDao = new UserDaoImpl();
        userDao.deleteTable();
        for (int i = 0; i < homePage.user.size(); i++) {
            userDao.insert(homePage.user.get(i));
        }
        return homePage.user.size();
    }

    /**
     * 将读到的用户信息显示在页面
     */
    public void addUserToSurface() {
        String[] headers = {"姓名", "身份证号", "准考证号", "是否登录"};
        String[][] data = new String[homePage.user.size()][4];
        // 遍历user数组
        for (int i = 0; i < homePage.user.size(); i++) {
            data[i][0] = homePage.user.get(i).getUsername();
            data[i][1] = homePage.user.get(i).getIdentity();
            data[i][2] = homePage.user.get(i).getExamineeNumber();
            if (homePage.user.get(i).getIslogin()) {
                data[i][3] = "已登录";
            } else {
                data[i][3] = "未登录";
            }
        }

        TableModel dataModel = new DefaultTableModel(data, headers);
        homePage.table = new JTable(dataModel);
        //设置表格登录状态的背景字体
        homePage.table.getColumn(headers[3]).setCellRenderer(tcr);
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        // 创建滚动条面板，使table具有滚动条
        homePage.scrollPane = new JScrollPane(homePage.table);
        homePage.scrollPane.setPreferredSize(new Dimension(homePage.panel.getWidth(), homePage.panel.getHeight()));
        // 设置表格中单元格的高度
        homePage.table.setRowHeight(35);
        // 设置表格的列宽，注意在设置列宽的时候如果输入的值不合适会出错
        homePage.table.getColumn("姓名").setPreferredWidth(200);
        homePage.table.getColumn("身份证号").setPreferredWidth(200);
        homePage.table.getColumn("准考证号").setPreferredWidth(200);
        homePage.table.getColumn("是否登录").setPreferredWidth(200);
        // 设置内容不可修改
        homePage.table.setEnabled(false);
        //设置表格内容居中
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        homePage.table.setDefaultRenderer(Object.class, r);
        homePage.scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        // 将含滚动条的表格添加到panel上
        homePage.panel.add(homePage.scrollPane);
        // 刷新服务端姐界面的展示用户信息的面板
        homePage.panel.updateUI();
    }

    /**
     * 每隔两秒更新table中的登录状态
     */
    public void updateStatus() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String[] headers = {"姓名", "身份证号", "准考证号", "是否登录"};
                String[][] data = new String[homePage.user.size()][4];
                // 遍历user数组
                for (int i = 0; i < homePage.user.size(); i++) {
                    data[i][0] = homePage.user.get(i).getUsername();
                    data[i][1] = homePage.user.get(i).getIdentity();
                    data[i][2] = homePage.user.get(i).getExamineeNumber();
                    if (homePage.user.get(i).getIslogin()) {
                        data[i][3] = "已登录";
                    } else {
                        if(homePage.user.get(i).getSubmit()) {
                            data[i][3] = "已提交";
                        } else if(homePage.user.get(i).getException()) {
                            data[i][3] = "异常退出";
                        } else {
                            data[i][3] = "未登录";
                        }
                    }
                }
                TableModel dataModel = new DefaultTableModel(data, headers);
                homePage.table.setModel(dataModel);
                //设置表格登录状态的背景字体
                homePage.table.getColumn(headers[3]).setCellRenderer(tcr);
                tcr.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }, 0, 2000);
    }
}
