package com.tianyufighter.draw;

import javax.swing.*;
import java.awt.*;

/**
 * 当考试时间还没有到时弹出该界面，提示考生还没到考试时间
 */
public class PopupMenu {
    // 创建窗口
    public JFrame frame = null;

    public PopupMenu() {
        initialize();
    }

    /**
     * 初始化窗口，并把组件添加到窗口上
     */
    private void initialize() {
        frame = new JFrame();
        //加载图片
        ImageIcon icon=new ImageIcon("D:\\10.jpg");
        //Image im=new Image(icon);
        //将图片放入label中
        JLabel label=new JLabel(icon);

        //设置label的大小
        label.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        //获取窗口的第二层，将label放入
        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
        //获取frame的顶层容器,并设置为透明
        JPanel j=(JPanel)frame.getContentPane();
        frame.setSize(400, 500);
        j.setOpaque(false);

        // 显示提示用户考试时间还没到
        JLabel FontLabel = new JLabel();
        FontLabel.setText("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;亲，考试时间未到额，<br/>" +
                "<br/>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请等待考试时间到了<br/>" +
                "<br/>"+
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;才能答题......</html>");
        FontLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 23));
        FontLabel.setForeground(Color.GRAY);
        frame.add(FontLabel);
        // 设置窗体相对于另一个组件居中，参数null表示窗体相对于屏幕的中央位置
        frame.setLocationRelativeTo(null);
        // 设置禁止调整窗口的大小
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        PopupMenu popupMenu = new PopupMenu();
    }
}
