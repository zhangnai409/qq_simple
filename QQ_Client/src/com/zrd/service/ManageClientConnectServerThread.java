package com.zrd.service;

import java.util.HashMap;

/**
 * ����ͻ������ӷ���˵��߳���
 */
public class ManageClientConnectServerThread {
    // �Ѷ��̷߳���һ�� HashMap ���ϣ�key �����û�id�� value�����߳�
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    // ��ĳ���̷߳��뼯��
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    // ͨ�� userId �õ���Ӧ�߳�
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }
}
