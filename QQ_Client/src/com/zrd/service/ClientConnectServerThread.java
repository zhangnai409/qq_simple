package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientConnectServerThread extends Thread {
    // 该线程需要持有 Socket
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 因为 Thread需要在后端和服务器通信，我们要用 while循环
        while (true) {
            try {
                System.out.println("客户端线程，等待读取从服务器端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // 若服务器没有发送 Message对象，线程会阻塞在这里
                Message message = (Message) ois.readObject();
                // 判断这个 message类型，然后做相应的业务处理
                if (message.getMessType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    // 取出在线列表信息，并展示
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n=========当前在线用户列表===========");
                    for (String i : onlineUsers) {
                        System.out.println("用户: " + i);
                    }
                } else if (message.getMessType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // 从服务器转发的消息，显示
                    Date date = new Date(message.getSendTime());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
                    String format = simpleDateFormat.format(date);
                    System.out.println("\n【" + format + "】 【" + message.getSender() + "】 对 【"
                            + message.getGetter() + "】 说: " + message.getContent());
                } else if (message.getMessType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    // 显示
                    Date date = new Date(message.getSendTime());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
                    String format = simpleDateFormat.format(date);
                    System.out.println("\n【" + format + "】 【" + message.getSender() + "】 群聊: " + message.getContent());
                } else if (message.getMessType().equals(MessageType.MESSAGE_FILE_MES)) {
                    System.out.println("\n【" + message.getSender() + "】 给 " + message.getGetter() + " 发送文件: " + message.getSrc()
                            + " 到【" + message.getGetter() + "】的电脑目录: " + message.getDest());
                    // 取出 message的文件字节数组，通过文件输出流写到磁盘
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("\n文件保存成功!");
                } else {
                    System.out.println("其他业务，暂无服务..");
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
