package com.tianyufighter.actListener;

import com.tianyufighter.draw.HomePage;
import com.tianyufighter.util.MyTableCellRender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 服务端点击刷新按钮的监听事件，刷新表格
 */
public class RenovateListener implements ActionListener {
    private HomePage homePage;
    private MyTableCellRender tcr = new MyTableCellRender();

    public RenovateListener(HomePage homePage) {
        this.homePage = homePage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
        JOptionPane.showMessageDialog(null, "表中的数据刷新完成");
    }
}
