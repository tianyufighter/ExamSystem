package com.tianyufighter.domain;

import com.tianyufighter.service.Server;

/**
 * 启动服务端入口
 */
public class Entry{
    public static void main(String[] args) {
        new Server(8080).start();
    }
}
