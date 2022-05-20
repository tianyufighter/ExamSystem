package com.tianyufighter.util;

import com.tianyufighter.draw.HomePage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 每隔两秒更新服务端界面的在线人数的标签
 */
public class UpdateOnline {
    private HomePage homePage;

    public UpdateOnline(HomePage homePage) {
        this.homePage = homePage;
    }

    /**
     * 启动该更新功能
     */
    public void execute() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                homePage.label1.setText("考生在线人数: " + homePage.onlinePeople + " / 30");
            }
        }, 0, 2000);
    }
}
