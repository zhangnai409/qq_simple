package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;
import com.zrd.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ���������� 9999 �˿ڼ������ȴ��û��˵����ӣ�������ͨ��
 */
public class Server {
    private ServerSocket serverSocket = null;

    // ����һ�����ϴ�Ŷ���û�
    // ConcurrentHashMap���Դ������ļ��ϣ��̰߳�ȫ
    // HashMap û�д����̰߳�ȫ������ڶ��̲߳���ȫ
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
    // ���߷����ļ�
    private static ConcurrentHashMap<String, ArrayList<Message>> offLineDb = new ConcurrentHashMap<>();
    static { // ��̬����飬��ʼ��һ��
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("�ĺ�", new User("�ĺ�", "666666"));
        validUsers.put("����", new User("����", "88888888"));
        validUsers.put("Ӱ", new User("Ӱ", "55555"));
    }

    // ��֤�û��Ƿ�Ϸ�
    private boolean checkUser(String userId, String pwd) {
        User user = validUsers.get(userId);
        if (user == null) { // ˵�� userIdû�д��� validUsers��key��
            return false;
        }
        if (!user.getPwd().equals(pwd)) { // userId��ȷ�����������
            return false;
        }
        return true;
    }

    public Server() {
        // ע�⣺�˿ڿ���д�������ļ�
        try {
            System.out.println("������ �� 9999 �˿ڼ���...");
            // �����������ŷ���
            new Thread(new SendNewsToAllService()).start();

            serverSocket = new ServerSocket(9999);
            // ����ĳ���ͻ������Ӻ󣬻��������
            while (true) {
                Socket socket = serverSocket.accept();
                // �õ�socket�����Ķ���������
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // �õ�socket�����Ķ��������
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                // ��ȡ�ͻ��˷��͵�user����
                User user = (User) ois.readObject();
                // ����Message����׼���ظ��ͻ���
                Message message = new Message();
                // ��֤
                if (checkUser(user.getUserId(), user.getPwd())) {
                    message.setMessType(MessageType.MESSAGE_LOGIN_SUCCESS);
                    // �� Message ����ظ��ͻ���
                    oos.writeObject(message);
                    // ����һ���̺߳Ϳͻ��˱���ͨ��
                    ServerConnectClientThread serverConnectClientThread =
                            new ServerConnectClientThread(socket, user.getUserId());
                    // �����߳�
                    serverConnectClientThread.start();
                    // �����̷߳��뵽�����й���
                    ManageClientThreads.addClientThread(user.getUserId(), serverConnectClientThread);
                } else {
                    // ��¼ʧ��
                    System.out.println("�û�: ��" + user.getUserId() + "�� ����: " + user.getPwd() + " ��֤ʧ��");
                    message.setMessType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    // �ر�socket
                    socket.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // ���������˳� whileѭ����˵�����������ڼ�������˹ر�serverSocket
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
