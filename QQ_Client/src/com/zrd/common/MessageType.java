package com.zrd.common;

/**
 * ��Ϣ����
 */
public interface MessageType {
    // �ڽӿ��ж���һЩ��������ʾ��ͬ����Ϣ����
    String MESSAGE_LOGIN_SUCCESS = "1";// ��½�ɹ�
    String MESSAGE_LOGIN_FAIL = "2"; // ��¼ʧ��
    String MESSAGE_COMM_MES = "3";// ��ͨ��Ϣ��
    String MESSAGE_GET_ONLINE_FRIEND = "4";// Ҫ�󷵻������û��б�
    String MESSAGE_RET_ONLINE_FRIEND = "5";// ���������û��б�
    String MESSAGE_CLIENT_EXIT = "6";// �ͻ��������˳�
    String MESSAGE_TO_ALL_MES = "7";// Ⱥ����Ϣ
    String MESSAGE_FILE_MES = "8";//�����ļ�
}
