package com.zrd.service;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 管理和客户端通信的线程
 */
public class ManageClientThreads {
    public static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    // 添加线程对象到hm集合
    public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    // 根据 userId 返回 ServerConnectClientThread 线程
    public static ServerConnectClientThread getClientThread(String userId) {
        return hm.get(userId);
    }

    // 从集合移除线程
    public static void removeServerConnectClientThread(String userId) {
        hm.remove(userId);
    }

    // 返回用户在线列表
    public static String getOnlineUsers() {
        // 集合遍历，遍历 HashMap 的 key
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUsersList = "";
        while (iterator.hasNext()) {
            onlineUsersList += iterator.next().toString() + " ";
        }
        return onlineUsersList;
    }
}
