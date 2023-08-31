package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * �����һ�������ĳ���ͻ��˱���ͨ��
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId; // ���ӵ�����˵��û�id

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    // �����̳߳��� run��״̬�����Է��ͻ������Ϣ
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("����˺Ϳͻ��� ��" + userId + "�� ����ͨ�ţ���ȡ����...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                // ���� Message��������Ӧҵ����
                if (message.getMessType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    // �û���Ҫ�����û��б�
                    System.out.println("��" + message.getSender() + "�� Ҫ�����û��б�");
                    String onlineUsers = ManageClientThreads.getOnlineUsers();
                    // ���� message
                    Message message2 = new Message();
                    message2.setMessType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message2.setContent(onlineUsers);
                    message2.setGetter(message.getSender());
                    // ���ؿͻ���
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                } else if (message.getMessType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // ���� message ��ȡ getter id��Ȼ���ڵõ���Ӧ�߳�
                    ServerConnectClientThread serverConnectClientThread =
                            ManageClientThreads.getClientThread(message.getGetter());
                    // �õ���Ӧ socket�Ķ������������message����ת����ָ���Ŀͻ���
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message); // ת������ʾ���û������ߣ����Ա��浽���ݿ�
                } else if (message.getMessType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    // ��Ҫ���� �����̵߳ļ��ϣ������е��̵߳�socket�õ���Ȼ��� message����ת��
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        // ȡ���û� id
                        String onLineUserId = iterator.next().toString();
                        // �ų�Ⱥ����Ϣ�û���
                        if (!onLineUserId.equals(message.getSender())) {
                            // ����ת��
                            ObjectOutputStream oos = new ObjectOutputStream(
                                    hm.get(onLineUserId).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }
                } else if (message.getMessType().equals(MessageType.MESSAGE_FILE_MES)) {
                    // ���� getterId ��ȡ��Ӧ���߳�
                    ObjectOutputStream oos = new ObjectOutputStream(
                            ManageClientThreads.getClientThread(message.getGetter())
                                    .getSocket().getOutputStream());
                    // ת��
                    oos.writeObject(message);
                } else if (message.getMessType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    // �ͻ����˳�
                    System.out.println("��" + message.getSender() + "�� �˳�");
                    // ������ͻ��˶�Ӧ�̴߳��̼߳������Ƴ�
                    ManageClientThreads.removeServerConnectClientThread(message.getSender());
                    socket.close(); // �ر�����
                    // �˳�ѭ��
                    break;
                } else {
                    System.out.println("����ҵ�������δ��ͨ...");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
