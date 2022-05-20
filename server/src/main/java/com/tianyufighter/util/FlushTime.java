package com.tianyufighter.util;

import com.tianyufighter.draw.HomePage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 每隔1秒更新服务端的时间
 */
public class FlushTime {
    // 服务端的页面对象
    private HomePage homePage = null;

    public FlushTime() {
    }

    public FlushTime(HomePage homePage) {
        this.homePage = homePage;
    }

    /**
     * 调用该方法后，每隔一秒刷新服务端页面的时间
     */
    public void execute() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String s = getCurrentTime();
                openCountDown(homePage.testTime, s, homePage.testLongTime);
                homePage.label3.setText("当前时间: " + s);
                homePage.label3.updateUI();
            }
        }, 0, 1000);
    }

    /**
     * 得到当前的时间，并将得到的时间转化成指定的字符串格式
     * @return 日期字符串
     */
    public String getCurrentTime() {
        Calendar nowTime = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String str = fmt.format(nowTime.getTime());
        return str;
    }

    /**
     * 当用户设定的时间和当前的时间相等时，开启倒计时功能
     * @param str1 用户设定的时间
     * @param str2 当前系统的时间
     * @param testLongTime 考试时长
     */
    public void openCountDown(String str1, String str2, String testLongTime) {
        if(str2.equals(str1)) {
            // 启动倒计时功能，每隔1秒刷新一下存放倒计时的面板
            CountDown countDown = new CountDown(homePage, Integer.valueOf(testLongTime) * 60);
            countDown.execute();
        }
    }
}
