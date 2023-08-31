package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * �ṩ����Ϣ��صķ��񷽷�
 */
public class MessageClientService {
    /**
     * ˽��
     *
     * @param content  ��������
     * @param senderId ������
     * @param getterId ������
     */
    public void sendMessageToOne(String content, String senderId, String getterId) {
        Message message = new Message();
        message.setMessType(MessageType.MESSAGE_COMM_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println("��" + senderId + "�� �� ��" + getterId + "�� ˵ " + content);
        // ���͸������
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread
                            .getClientConnectServerThread(senderId)
                            .getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ⱥ����Ϣ
     *
     * @param content
     * @param senderId
     */
    public void sendMessageToAll(String content, String senderId) {
        Message message = new Message();
        message.setMessType(MessageType.MESSAGE_TO_ALL_MES);
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println("��" + senderId + "�� ��Ⱥ������Ϊ: " + content);
        // ���͸������
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread
                            .getClientConnectServerThread(senderId)
                            .getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
