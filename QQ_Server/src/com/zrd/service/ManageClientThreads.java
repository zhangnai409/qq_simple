package com.zrd.service;

import java.util.HashMap;
import java.util.Iterator;

/**
 * ����Ϳͻ���ͨ�ŵ��߳�
 */
public class ManageClientThreads {
    public static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    // ����̶߳���hm����
    public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        hm.put(userId, serverConnectClientThread);
    }

    // ���� userId ���� ServerConnectClientThread �߳�
    public static ServerConnectClientThread getClientThread(String userId) {
        return hm.get(userId);
    }

    // �Ӽ����Ƴ��߳�
    public static void removeServerConnectClientThread(String userId) {
        hm.remove(userId);
    }

    // �����û������б�
    public static String getOnlineUsers() {
        // ���ϱ��������� HashMap �� key
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUsersList = "";
        while (iterator.hasNext()) {
            onlineUsersList += iterator.next().toString() + " ";
        }
        return onlineUsersList;
    }
}
