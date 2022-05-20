package com.tianyufighter.client;

import com.tianyufighter.draw.Login;
import com.tianyufighter.function.ReceiveMessage;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端启动入口
 */
public class StudentClient {
    public static void main(String[] args) {
        Socket connection = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            connection = new Socket("127.0.0.1", 8080);
            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());
            ReceiveMessage receiveMessage = new ReceiveMessage(in, out);
            receiveMessage.start();
            // 启动登录窗口的类
            new Login(in, out);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "服务端正在维护中，请稍后登录");
        }
    }
}
