package com.tianyufighter.util;



import com.tianyufighter.draw.HomePage;
import com.tianyufighter.service.ReadAndWrite;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 计时器
 * @author 心灵之约
 *
 */
public class CountDown {
    // 记录倒计时的时间的时，分，秒
    private long hour;
    private long minute;
    private long second;
    // 考试剩余的时间(单位: 秒)
    private long totalSec;
    private long tempTotalSec;
    private JPanel jp;
    // 关闭结束定时器
    private boolean isClose = false;
    // 服务端界面对象
    private HomePage homePage = null;
    // 当flag为ture时才可以向客户端发送倒计时消息(即服务端在向客户端发送对象的时候，倒计时消息不能发送)
    public static boolean flag = true;

    public CountDown() {
    }

    public CountDown(HomePage homePage, long totalSec) {
        this.totalSec = totalSec;
        this.tempTotalSec = totalSec;
        this.homePage = homePage;
    }

    /**
     * 每隔一秒在面板上显示剩余的考试时间
     */
    public void execute() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	if(isClose) {
            		timer.cancel();
            	}
                if(tempTotalSec >= 0) {
                    String s = changeFormat(tempTotalSec);
                    // 向客户端发送该倒计时信息
                    if(flag) {
                        try {
                            homePage.out.writeUTF("countdown|" + s);
                        } catch (Exception e) {
                            if(ReadAndWrite.nowUser != null) {
                                ReadAndWrite.nowUser.setException(true);
                            }
                            flag = false;
                        }
                    }
                    // 重新设置服务端页面存放倒计时的标签的值
                    homePage.label2.setText(s);
                    homePage.label2.updateUI();
                    tempTotalSec--;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    /**
     * 将剩余的秒数转化成对应格式的字符串
     * @param totalSec 剩余的秒数
     * @return 在面板中显示的字符串
     */
    public  String changeFormat(long totalSec) {
        hour = totalSec / 60 / 60 % 60;
        minute = totalSec / 60 % 60;
        second = totalSec % 60;
        return "距离考试结束时间:" + String.format("%02d", hour) + "小时:" + String.format("%02d", minute) + "分:" + String.format("%02d", second) + "秒";
    }
    /**
     * 关闭定时器
     */
    public void stop() {
    	isClose = true;
    }
    /**
     * 重置数据
     */
    public void reset() {
    	tempTotalSec = totalSec;
    }

    public static void main(String[] args) {
        CountDown countDown = new CountDown(null, 200);
        countDown.execute();
    }
}
