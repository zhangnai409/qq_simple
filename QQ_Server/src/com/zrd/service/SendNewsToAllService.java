package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;
import com.zrd.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class SendNewsToAllService implements Runnable {

    @Override
    public void run() {
        // Ϊ�˶����������
        while (true) {
            System.out.println("�������������Ҫ���͵���Ϣ/��Ϣ[���� exit ��ʾ�˳����ͷ���]");
            String news = Utility.readString(200);
            if ("exit".equals(news)) {
                break;
            }
            // ������Ϣ
            Message message = new Message();
            message.setSender("������");
            message.setMessType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("������������Ϣ�������� ˵: " + news);
            // ������ǰ����ͨ���߳�
            HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onLineUserId = iterator.next();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(
                            hm.get(onLineUserId).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
