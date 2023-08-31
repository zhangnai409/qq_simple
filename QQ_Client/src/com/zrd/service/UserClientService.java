package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;
import com.zrd.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * �û���¼��֤���û�ע��ȹ���
 */
public class UserClientService {
    // �����������ط�ʹ��user��Ϣ������������Ա����
    private User user = new User();
    // �����������ط�ʹ��socket��Ϣ������������Ա����
    private Socket socket;

    /**
     * ���� userId �� pwd ����������֤���û��Ƿ�Ϸ�
     *
     * @param userId
     * @param pwd
     * @return
     */
    public boolean login(String userId, String pwd) {
        boolean b = false;
        // ��������
        user.setUserId(userId);
        user.setPwd(pwd);
        // ���ӷ����������� user ����
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            // �õ� ObjectOutputStream ����
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // ���� User ����
            oos.writeObject(user);

            // ��ȡ�ӷ������ظ��� Message����
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMessType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)) {
                // ��½�ɹ�
                // ����һ���ͷ���������ͨ�ŵ��߳�
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                // �����ͻ��˵��߳�
                clientConnectServerThread.start();
                // Ϊ�˺���ͻ��˵���չ,����Ҫ���̷߳��뼯�������й���
                ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
                b = true;
            } else {
                // ��¼ʧ��,���ǾͲ��������ͷ�����ͨ�ŵ��̣߳��ر�socket
                socket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    /**
     * ���������������û��б�
     */
    public void onlineFriendList() {
        // ����һ�� Message������Ϊ MESSAGE_GET_ONLINE_FRIEND
        Message message = new Message();
        message.setMessType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getUserId());

        // ���͸�������
        // �õ���ǰ Socket ��Ӧ�� ObjectOutputStream ����
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId())
                                    .getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * �˳��ͻ��ˣ��ظ�����˷���һ���˳�ϵͳ�� Message����
     */
    public void logout() {
        try {
            Message message = new Message();
            message.setMessType(MessageType.MESSAGE_CLIENT_EXIT);
            message.setSender(user.getUserId()); // һ��Ҫָ���ĸ��ͻ���Ҫ�˳�
            // ���� message
            // ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread
                            .getClientConnectServerThread(user.getUserId())
                            .getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println("��" + user.getUserId() + "���˳�ϵͳ");
            System.exit(0); // ��������
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
