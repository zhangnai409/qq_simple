package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientConnectServerThread extends Thread {
    // ���߳���Ҫ���� Socket
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // ��Ϊ Thread��Ҫ�ں�˺ͷ�����ͨ�ţ�����Ҫ�� whileѭ��
        while (true) {
            try {
                System.out.println("�ͻ����̣߳��ȴ���ȡ�ӷ������˷��͵���Ϣ");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // ��������û�з��� Message�����̻߳�����������
                Message message = (Message) ois.readObject();
                // �ж���� message���ͣ�Ȼ������Ӧ��ҵ����
                if (message.getMessType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    // ȡ�������б���Ϣ����չʾ
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n=========��ǰ�����û��б�===========");
                    for (String i : onlineUsers) {
                        System.out.println("�û�: " + i);
                    }
                } else if (message.getMessType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // �ӷ�����ת������Ϣ����ʾ
                    Date date = new Date(message.getSendTime());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss E");
                    String format = simpleDateFormat.format(date);
                    System.out.println("\n��" + format + "�� ��" + message.getSender() + "�� �� ��"
                            + message.getGetter() + "�� ˵: " + message.getContent());
                } else if (message.getMessType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    // ��ʾ
                    Date date = new Date(message.getSendTime());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss E");
                    String format = simpleDateFormat.format(date);
                    System.out.println("\n��" + format + "�� ��" + message.getSender() + "�� Ⱥ��: " + message.getContent());
                } else if (message.getMessType().equals(MessageType.MESSAGE_FILE_MES)) {
                    System.out.println("\n��" + message.getSender() + "�� �� " + message.getGetter() + " �����ļ�: " + message.getSrc()
                            + " ����" + message.getGetter() + "���ĵ���Ŀ¼: " + message.getDest());
                    // ȡ�� message���ļ��ֽ����飬ͨ���ļ������д������
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n�ļ�����ɹ�!");
                } else {
                    System.out.println("����ҵ�����޷���..");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
