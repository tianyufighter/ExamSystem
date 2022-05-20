package com.tianyufighter.actionListener;

import com.tianyufighter.draw.AnswerInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CheckBoxListener implements ActionListener {
    private AnswerInterface answerInterface;

    public CheckBoxListener(AnswerInterface answerInterface) {
        this.answerInterface = answerInterface;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 1; i < answerInterface.checkBox.size(); i++) {
            if(answerInterface.checkBox.get(i).isSelected()) {
                answerInterface.judgeRecord.set(i, 1);
            } else {
                answerInterface.judgeRecord.set(i, 0);
            }
        }
        // 向服务端发送做题的情况
        try {
            // 向服务端发送要发送信息的类型
            answerInterface.out.writeUTF("testOperation");
            ObjectOutputStream tempOut = new ObjectOutputStream(answerInterface.out);
            // 将存储选择题的list集合传给服务端
            tempOut.writeObject(answerInterface.choiceRecord);
            // 将存储判断题的list集合传给服务端
            tempOut.writeObject(answerInterface.judgeRecord);
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
    }
}
