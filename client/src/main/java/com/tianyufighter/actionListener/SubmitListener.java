package com.tianyufighter.actionListener;

import com.tianyufighter.draw.AnswerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SubmitListener implements ActionListener {
    private AnswerInterface answerInterface = null;

    public SubmitListener(AnswerInterface answerInterface) {
        this.answerInterface = answerInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * 向服务端发送消息，让服务端不在向客户端发送消息
         */
        int res=JOptionPane.showConfirmDialog(null, "是否提交试卷?", "是否交卷", JOptionPane.YES_NO_OPTION);
        // 如果考生点击交卷
        if(res==JOptionPane.YES_OPTION){
            try {
                answerInterface.out.writeUTF("stop");
                // 隐藏试卷
                answerInterface.topPanel.setVisible(false);
                answerInterface.panel.setVisible(false);
                answerInterface.bottomPanel.setVisible(false);
                answerInterface.scrollPane.setVisible(false);
                // 显示弹框
                JOptionPane.showMessageDialog(answerInterface.frame, "考试完成，祝您好运😊", "消息", JOptionPane.INFORMATION_MESSAGE);
                answerInterface.in.close();
                answerInterface.out.close();
                answerInterface.frame.dispose();
                System.exit(0);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else {
            // System.out.println("选择否后执行的代码");    //点击“否”后执行这个代码块
            return;
        }
    }
}
