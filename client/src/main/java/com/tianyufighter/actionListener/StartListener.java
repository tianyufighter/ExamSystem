package com.tianyufighter.actionListener;

import com.tianyufighter.draw.AnswerInterface;
import com.tianyufighter.draw.ProgressWindow;
import com.tianyufighter.model.ChoiceQuestion;
import com.tianyufighter.model.JudgeQuestion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class StartListener implements ActionListener {
    // 显示考场规则界面的frame的对象
    private JFrame frame;
    // 客户端的输入流
    private DataInputStream in;
    // 客户端的输出流
    private DataOutputStream out;
    /**
     * 初始化，将考试规则界面的信息赋值给当前类的变量
     * @param frame 考场规则界面的对象
     * @param in 客户端的输入流
     * @param out 客户端的输出流
     */
    public StartListener(JFrame frame, DataInputStream in, DataOutputStream out) {
        this.frame = frame;
        this.in = in;
        this.out = out;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 向服务端发送消息，请求考试题
        try {
            out.writeUTF("testPaper");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        frame.dispose();
    }
}
