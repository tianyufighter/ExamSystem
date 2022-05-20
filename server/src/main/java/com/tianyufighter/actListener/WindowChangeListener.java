package com.tianyufighter.actListener;

import com.tianyufighter.draw.HomePage;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * 窗口变化的监听事件
 */
public class WindowChangeListener extends ComponentAdapter {
    // 服务端界面的对象
    private HomePage homePage = null;

    public WindowChangeListener(HomePage homePage) {
        this.homePage = homePage;
    }
    @Override
    public void componentResized(ComponentEvent e) {
        // 窗口的宽度
        int fraWidth = homePage.frame.getWidth();
        // 窗口的高度
        int fraHeight = homePage.frame.getHeight();
//        System.out.println("窗口的宽度: " + homePage.frame.getWidth());
//        System.out.println("窗口的高度: " + homePage.frame.getWidth());
//        System.out.println("面板的宽度: " + homePage.panel.getWidth());
//        System.out.println("面板的高度: " + homePage.panel.getHeight());
        // 设置图片的大小
        homePage.label.setBounds(0, 0, fraWidth, fraHeight);
        if(homePage.scrollPane != null && homePage.panel != null) {
            homePage.scrollPane.setPreferredSize(new Dimension(homePage.panel.getWidth(), homePage.panel.getHeight()));
        }
        homePage.panel.updateUI();
        homePage.label.updateUI();
    }

}
