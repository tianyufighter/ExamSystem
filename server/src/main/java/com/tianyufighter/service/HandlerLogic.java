package com.tianyufighter.service;

import com.tianyufighter.draw.HomePage;
import com.tianyufighter.model.User;
import com.tianyufighter.util.StoreCurrentUser;

import java.io.*;
import java.net.Socket;

public class HandlerLogic extends Thread{
    // 与客户端建立新的连接时创建的Socket对象
    private Socket connection;
    // 服务器端的界面对象
    private HomePage homePage;
    // 记录当前的登录用户
    public StoreCurrentUser storeCurrentUser = null;
    // 存当前的用户
    public  User user = null;


//    public HandlerLogic(Socket connection) {
//        this.connection = connection;
//        this.start();
//    }

    public HandlerLogic(Socket connection, HomePage homePage) {
        this.connection = connection;
        this.homePage = homePage;
        this.start();
    }

    @Override
    public void run() {
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());
            // 将服务端的输入流和输出流传给服务器页面对象
            homePage.in = in;
            homePage.out = out;
            String info = "";
            // 如果当读取的字符串的为QUIT则结束通信
            while(!info.equalsIgnoreCase("QUIT")) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    info = in.readUTF();
                    new ReadAndWrite(in, out, info, homePage, this);
                } catch (IOException e) {
                    // 服务端与客户端的连接断开
                    new ReadAndWrite(null, null, "disconnect", homePage, this);
                    // 服务端不在进行读取数据
                    break;
                }
            }
            System.out.println("服务端与客户端交互完成，正在关闭它们之间的通信");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("与服务器的连接断开了");
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
