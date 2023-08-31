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
 * 服务器，在 9999 端口监听，等待用户端的连接，并保持通信
 */
public class Server {
    private ServerSocket serverSocket = null;

    // 创建一个集合存放多个用户
    // ConcurrentHashMap可以处理并发的集合，线程安全
    // HashMap 没有处理线程安全，因此在多线程不安全
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
    // 离线发送文件
    private static ConcurrentHashMap<String, ArrayList<Message>> offLineDb = new ConcurrentHashMap<>();
    static { // 静态代码块，初始化一次
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("心海", new User("心海", "666666"));
        validUsers.put("胡桃", new User("胡桃", "88888888"));
        validUsers.put("影", new User("影", "55555"));
    }

    // 验证用户是否合法
    private boolean checkUser(String userId, String pwd) {
        User user = validUsers.get(userId);
        if (user == null) { // 说明 userId没有存在 validUsers的key中
            return false;
        }
        if (!user.getPwd().equals(pwd)) { // userId正确，但密码错误
            return false;
        }
        return true;
    }

    public Server() {
        // 注意：端口可以写在配置文件
        try {
            System.out.println("服务器 在 9999 端口监听...");
            // 启动推送新闻服务
            new Thread(new SendNewsToAllService()).start();

            serverSocket = new ServerSocket(9999);
            // 当和某个客户端连接后，会持续监听
            while (true) {
                Socket socket = serverSocket.accept();
                // 得到socket关联的对象输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // 得到socket关联的对象输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                // 读取客户端发送的user对象
                User user = (User) ois.readObject();
                // 创建Message对象，准备回复客户端
                Message message = new Message();
                // 验证
                if (checkUser(user.getUserId(), user.getPwd())) {
                    message.setMessType(MessageType.MESSAGE_LOGIN_SUCCESS);
                    // 将 Message 对象回复客户端
                    oos.writeObject(message);
                    // 创建一个线程和客户端保持通信
                    ServerConnectClientThread serverConnectClientThread =
                            new ServerConnectClientThread(socket, user.getUserId());
                    // 启动线程
                    serverConnectClientThread.start();
                    // 将该线程放入到集合中管理
                    ManageClientThreads.addClientThread(user.getUserId(), serverConnectClientThread);
                } else {
                    // 登录失败
                    System.out.println("用户: 【" + user.getUserId() + "】 密码: " + user.getPwd() + " 验证失败");
                    message.setMessType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    // 关闭socket
                    socket.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 若服务器退出 while循环，说明服务器不在监听，因此关闭serverSocket
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
