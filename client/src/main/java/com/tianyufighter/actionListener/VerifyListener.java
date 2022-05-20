package com.tianyufighter.actionListener;

import com.tianyufighter.function.ValidCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 监听登录界面的类
 */
public class VerifyListener implements ActionListener {
    private ValidCode validCode;
    private ImageIcon verifyImage;
    private JLabel label1;
    public VerifyListener(ValidCode validCode, ImageIcon verifyImage, JLabel label1) {
        this.validCode = validCode;
        this.verifyImage = verifyImage;
        this.label1 = label1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        verifyImage.setImage(validCode.getImage());
        label1.updateUI();
    }
}
