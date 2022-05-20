package com.tianyufighter.actionListener;

import com.tianyufighter.draw.Login;
import com.tianyufighter.function.ValidCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 监听登录界面的类
 */
public class LoginListener implements ActionListener {
    //账号输入框对象
    private javax.swing.JTextField jt;
    //密码输入框对象
    private javax.swing.JPasswordField jp;
    //定义登录界面的对象
    private Login login;
    // 定义验证码的对象
    private ValidCode validCode;
    // 验证码输入框对象
    private JTextField yanzheng;
    // 客户端的输入流
    private DataInputStream in;
    // 客户端的输出流
    private DataOutputStream out;
    // 存取服务端传回的结果
    private String info = "";

    public LoginListener(DataInputStream in, DataOutputStream out, JTextField jt, JPasswordField jp,JTextField yanzheng, ValidCode validCode, Login login) {
        // 获取登录界面中的账号输入框对象
        this.jt = jt;
        // 获取登录界面中的密码输入框对象
        this.jp = jp;
        // 获取登录界面
        this.login = login;
        // 获取画验证码的对象
        this.validCode = validCode;
        // 获取登录界面中的验证码输入框
        this.yanzheng = yanzheng;
        // 获取客户端的输入流
        this.in = in;
        // 获取客户端的输出流
        this.out = out;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 如果验证码正确
        if(yanzheng.getText().equals(validCode.getText())) {
            try {
                // 向服务器端发送登录信息
                out.writeUTF("login|examinee_number|"+jt.getText()+"|password|" + new String(jp.getPassword()));
            } catch (NullPointerException exception) {
                System.out.println("没有连接服务器");
            } catch (IOException ioException) {
                System.out.println("读取完成");
            }

        } else {
            JOptionPane.showMessageDialog(null, "验证码有误！！", "Error",JOptionPane.ERROR_MESSAGE);
            // 换一张验证码
            login.button1.doClick();
            // 验证码输入框的内容清空
            yanzheng.setText("");
            System.out.println("验证码有误！！");
        }
    }
}
