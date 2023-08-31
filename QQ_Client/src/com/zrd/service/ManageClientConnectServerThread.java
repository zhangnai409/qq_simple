package com.zrd.service;

import java.util.HashMap;

/**
 * 管理客户端连接服务端的线程类
 */
public class ManageClientConnectServerThread {
    // 把多线程放入一个 HashMap 集合，key 就是用户id， value就是线程
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    // 将某个线程放入集合
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    // 通过 userId 得到对应线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }
}
