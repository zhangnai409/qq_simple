package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 该类的一个对象和某个客户端保持通信
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId; // 连接到服务端的用户id

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    // 这里线程出入 run的状态，可以发送或接受消息
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("服务端和客户端 【" + userId + "】 保持通信，读取数据...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                // 根据 Message类型作相应业务处理
                if (message.getMessType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    // 用户需要在线用户列表
                    System.out.println("【" + message.getSender() + "】 要在线用户列表");
                    String onlineUsers = ManageClientThreads.getOnlineUsers();
                    // 返回 message
                    Message message2 = new Message();
                    message2.setMessType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message2.setContent(onlineUsers);
                    message2.setGetter(message.getSender());
                    // 返回客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);
                } else if (message.getMessType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // 根据 message 获取 getter id，然后在得到对应线程
                    ServerConnectClientThread serverConnectClientThread =
                            ManageClientThreads.getClientThread(message.getGetter());
                    // 得到对应 socket的对象输出流，将message对象转发给指定的客户端
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message); // 转发，提示若用户不在线，可以保存到数据库
                } else if (message.getMessType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    // 需要遍历 管理线程的集合，把所有的线程的socket得到，然后把 message进行转发
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        // 取出用户 id
                        String onLineUserId = iterator.next().toString();
                        // 排除群发消息用户端
                        if (!onLineUserId.equals(message.getSender())) {
                            // 进行转发
                            ObjectOutputStream oos = new ObjectOutputStream(
                                    hm.get(onLineUserId).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }
                } else if (message.getMessType().equals(MessageType.MESSAGE_FILE_MES)) {
                    // 根据 getterId 获取对应的线程
                    ObjectOutputStream oos = new ObjectOutputStream(
                            ManageClientThreads.getClientThread(message.getGetter())
                                    .getSocket().getOutputStream());
                    // 转发
                    oos.writeObject(message);
                } else if (message.getMessType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    // 客户端退出
                    System.out.println("【" + message.getSender() + "】 退出");
                    // 将这个客户端对应线程从线程集合中移除
                    ManageClientThreads.removeServerConnectClientThread(message.getSender());
                    socket.close(); // 关闭连接
                    // 退出循环
                    break;
                } else {
                    System.out.println("其他业务服务，暂未开通...");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
