package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * �ļ��������
 */
public class FileClientService {
    /**
     * �����ļ�
     *
     * @param src      Դ�ļ�·��
     * @param dest     ���ļ����䵽��
     * @param senderId ������
     * @param getterId ������
     */
    public void sendFileToOne(String src, String dest, String senderId, String getterId) {
        // ��ȡԴ�ļ�
        Message message = new Message();
        message.setMessType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);
        message.setSendTime(new Date().toString());
        // ��ȡ�ļ�
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int) new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);
            // �� src�ļ����뵽�ֽ�����
            fileInputStream.read(fileBytes);
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // ��ʾ��Ϣ
        System.out.println("\n��" + senderId + "�� �� " + getterId + " �����ļ�: " + src
                + " ����" + getterId + "���ĵ���Ŀ¼: " + dest);
        // ����
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
