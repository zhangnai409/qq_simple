package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 提供和消息相关的服务方法
 */
public class MessageClientService {
    /**
     * 私聊
     *
     * @param content  聊天内容
     * @param senderId 发送者
     * @param getterId 接受者
     */
    public void sendMessageToOne(String content, String senderId, String getterId) {
        Message message = new Message();
        message.setMessType(MessageType.MESSAGE_COMM_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        System.out.println("【" + senderId + "】 对 【" + getterId + "】 说 " + content);
        // 发送给服务端
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
     * 群发消息
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
        System.out.println("【" + senderId + "】 发群聊内容为: " + content);
        // 发送给服务端
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
