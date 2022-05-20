package com.tianyufighter.function;

import com.tianyufighter.draw.AnswerInterface;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 更新倒计时的时间
 * 每隔一秒接收服务端发过来的倒计时信息
 */
public class UpdateTime {
    private AnswerInterface answerInterface = null;

    public UpdateTime(AnswerInterface answerInterface) {
        this.answerInterface = answerInterface;
    }

    /**
     * 每隔1秒接收服务端发送过来的倒计时消息
     */
    public void execute() {
//
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println("开始接收了");
//                    String str = answerInterface.in.readUTF();
////                    String[] res = str.split("\\|");
//                    System.out.println("接收完了");
////                    System.out.println(res[0]);
////                    System.out.println(res[1]);
//                    if("countdown".equals(res[0])) {
//                         answerInterface.time.setText(res[1]);
//                    } else {
//                        System.out.println("从服务端获取到了消息，但不是倒计时消息");
//                    }
//                } catch (IOException e) {
//                    System.out.println("没有获取到服务端传过来倒计时信息");
//                }
//            }
//        }, 0, 1000);
//        new Thread() {
//            @Override
//            public void run() {
//                while (true) {
//                    String line = "";
//                    try {
//                        line = answerInterface.in.readUTF();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("line的值 = " + line);
//                }
//            }
//        }.start();
    }
}
