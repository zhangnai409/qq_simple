package com.zrd.common;

import java.io.Serializable;

/**
 * 表示客户端和服务端通信时的消息对象
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender; // 发送者
    private String getter; // 接受者
    private String content; // 消息内容
    private String sendTime; // 发送时间
    private String messType; // 消息内容
    private byte[] fileBytes; // 存储文件字节数组
    private int fileSize = 0; // 文件大小
    private String dest; // 将文件传输到哪
    private String src; // 源文件路径

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getMessType() {
        return messType;
    }

    public void setMessType(String messType) {
        this.messType = messType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
