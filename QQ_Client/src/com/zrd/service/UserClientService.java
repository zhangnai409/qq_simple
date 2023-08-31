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
 * 用户登录验证和用户注册等功能
 */
public class UserClientService {
    // 可能在其他地方使用user信息，所以做出成员属性
    private User user = new User();
    // 可能在其他地方使用socket信息，所以做出成员属性
    private Socket socket;

    /**
     * 根据 userId 和 pwd 到服务器验证该用户是否合法
     *
     * @param userId
     * @param pwd
     * @return
     */
    public boolean login(String userId, String pwd) {
        boolean b = false;
        // 创建对象
        user.setUserId(userId);
        user.setPwd(pwd);
        // 连接服务器，发送 user 对象
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            // 得到 ObjectOutputStream 对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // 发送 User 对象
            oos.writeObject(user);

            // 读取从服务器回复的 Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMessType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)) {
                // 登陆成功
                // 创建一个和服务器保持通信的线程
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                // 启动客户端的线程
                clientConnectServerThread.start();
                // 为了后面客户端的扩展,我需要将线程放入集合容器中管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
                b = true;
            } else {
                // 登录失败,我们就不能启动和服务器通信的线程，关闭socket
                socket.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    /**
     * 向服务端请求在线用户列表
     */
    public void onlineFriendList() {
        // 发送一个 Message，类型为 MESSAGE_GET_ONLINE_FRIEND
        Message message = new Message();
        message.setMessType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(user.getUserId());

        // 发送给服务器
        // 得到当前 Socket 对应的 ObjectOutputStream 对象
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
     * 退出客户端，必给服务端发送一个退出系统的 Message对象
     */
    public void logout() {
        try {
            Message message = new Message();
            message.setMessType(MessageType.MESSAGE_CLIENT_EXIT);
            message.setSender(user.getUserId()); // 一定要指定哪个客户端要退出
            // 发送 message
            // ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(
                    ManageClientConnectServerThread
                            .getClientConnectServerThread(user.getUserId())
                            .getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println("【" + user.getUserId() + "】退出系统");
            System.exit(0); // 结束进程
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
