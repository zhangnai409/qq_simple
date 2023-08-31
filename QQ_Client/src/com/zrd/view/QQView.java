package com.zrd.view;

import com.zrd.service.FileClientService;
import com.zrd.service.MessageClientService;
import com.zrd.service.UserClientService;
import com.zrd.utils.Utility;

/**
 * �ͻ��˵Ĳ˵�ҳ��
 */
public class QQView {
    private boolean loop = true; // ���Ʋ˵��Ƿ���ʾ
    private String key = ""; // �����û���������
    // �����û���¼��ע��
    private UserClientService userClientService = new UserClientService();
    // �����û�˽��Ⱥ��
    private MessageClientService messageClientService = new MessageClientService();
    // �����ļ�
    private FileClientService fileClientService = new FileClientService();

    // ��ʾ���˵�
    public void mainMenu() {
        while (loop) {
            System.out.println("===========��ӭ��¼����ͨ��ϵͳ=============");
            System.out.println("\t\t\t 1 ��¼ϵͳ");
            System.out.println("\t\t\t 9 �˳�ϵͳ");
            System.out.println("----------------------------------------");
            System.out.print("����������ѡ��");

            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("�������û���");
                    String userId = Utility.readString(15);
                    System.out.print("�������� ��");
                    String pwd = Utility.readString(12);
                    // ���������֤�û��Ƿ�Ϸ�
                    if (userClientService.login(userId, pwd)) {
                        System.out.println("=========== ��ӭ���û� " + userId + " ����½�ɹ�==========");
                        // ��������˵�
                        while (loop) {
                            System.out.println("\n===========����ͨѶϵͳ�����˵����û� " + userId + "��==============");
                            System.out.println("\t\t\t 1 ��ʾ�����û��б�");
                            System.out.println("\t\t\t 2 Ⱥ����Ϣ");
                            System.out.println("\t\t\t 3 ˽����Ϣ");
                            System.out.println("\t\t\t 4 �����ļ�");
                            System.out.println("\t\t\t 9 �˳�ϵͳ");
                            System.out.println("------------------------------------------------");
                            System.out.println("���������ѡ��");

                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("������Ⱥ�����ݣ�");
                                    String content = Utility.readString(200);
                                    messageClientService.sendMessageToAll(content, userId);
                                    break;
                                case "3":
                                    System.out.println("��������������û���(����)��");
                                    String getterId = Utility.readString(20);
                                    System.out.println("��������˵�Ļ���");
                                    content = Utility.readString(200);
                                    messageClientService.sendMessageToOne(content, userId, getterId);
                                    break;
                                case "4":
                                    System.out.println("�����������(�����û�):");
                                    getterId = Utility.readString(20);
                                    System.out.println("�����뷢���ļ���·��(��ʽ�磺d:\\ xx.jpg)");
                                    String src = Utility.readString(120);
                                    System.out.println("��������ļ����͵������ߵĽ���·��(��ʽ�磺d:\\ xx.jpg)");
                                    String dest = Utility.readString(120);
                                    fileClientService.sendFileToOne(src, dest, userId, getterId);
                                    break;
                                case "9":
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("====== ��¼ʧ�� ========");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
