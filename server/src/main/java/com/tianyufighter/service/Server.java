package com.tianyufighter.service;

import com.tianyufighter.draw.HomePage;
import com.tianyufighter.draw.InitializePage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    // 服务器的端口号
    private int port;
    private ServerSocket server = null;
    private boolean running = true;
    public Server(int port) {
        this.port = port;
    }
    // 服务端界面的对象
//    public HomePage homePage = new HomePage();
    // 启动服务器的界面
    InitializePage initializePage = new InitializePage(100);
    @Override
    public void run() {
        initializePage.execute();
        // 开启服务端的界面
//        new Thread() {
//            @Override
//            public void run() {
//                initializePage.homePage.launch();
//            }
//        }.start();
        try {
//            server = new ServerSocket(port);
//            System.out.println("=====服务器启动完成====");
//            System.out.println("等待客户连接中^v^");
            int num = 0;
            while(running) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(HomePage.isConnect) {
                    if(server == null) {
                        server = new ServerSocket(port);
                        System.out.println("=====服务器启动完成====");
                        System.out.println("等待客户连接中^v^");
                    }
                    Socket connection = server.accept();
                    System.out.println(++num + "、新客户连接到了服务器--------");
                    HandlerLogic handlerLogic =new HandlerLogic(connection, initializePage.homePage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
