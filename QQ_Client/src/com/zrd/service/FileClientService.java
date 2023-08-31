package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * 文件传输服务
 */
public class FileClientService {
    /**
     * 单发文件
     *
     * @param src      源文件路径
     * @param dest     将文件传输到哪
     * @param senderId 发送者
     * @param getterId 接受者
     */
    public void sendFileToOne(String src, String dest, String senderId, String getterId) {
        // 读取源文件
        Message message = new Message();
        message.setMessType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);
        message.setSendTime(new Date().toString());
        // 读取文件
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int) new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);
            // 将 src文件读入到字节数组
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

        // 提示信息
        System.out.println("\n【" + senderId + "】 给 " + getterId + " 发送文件: " + src
                + " 到【" + getterId + "】的电脑目录: " + dest);
        // 发送
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
