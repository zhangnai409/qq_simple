package com.zrd.common;

import java.io.Serializable;

/**
 * ��ʾ�ͻ��˺ͷ����ͨ��ʱ����Ϣ����
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender; // ������
    private String getter; // ������
    private String content; // ��Ϣ����
    private String sendTime; // ����ʱ��
    private String messType; // ��Ϣ����
    private byte[] fileBytes; // �洢�ļ��ֽ�����
    private int fileSize = 0; // �ļ���С
    private String dest; // ���ļ����䵽��
    private String src; // Դ�ļ�·��

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
