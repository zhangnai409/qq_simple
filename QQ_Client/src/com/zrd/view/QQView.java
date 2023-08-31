package com.zrd.view;

import com.zrd.service.FileClientService;
import com.zrd.service.MessageClientService;
import com.zrd.service.UserClientService;
import com.zrd.utils.Utility;

/**
 * 客户端的菜单页面
 */
public class QQView {
    private boolean loop = true; // 控制菜单是否显示
    private String key = ""; // 接受用户键盘输入
    // 用于用户登录和注册
    private UserClientService userClientService = new UserClientService();
    // 对象用户私聊群聊
    private MessageClientService messageClientService = new MessageClientService();
    // 传送文件
    private FileClientService fileClientService = new FileClientService();

    // 显示主菜单
    public void mainMenu() {
        while (loop) {
            System.out.println("===========欢迎登录网络通信系统=============");
            System.out.println("\t\t\t 1 登录系统");
            System.out.println("\t\t\t 9 退出系统");
            System.out.println("----------------------------------------");
            System.out.print("请输入您的选择");

            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("请输入用户号");
                    String userId = Utility.readString(15);
                    System.out.print("请输入密 码");
                    String pwd = Utility.readString(12);
                    // 到服务端验证用户是否合法
                    if (userClientService.login(userId, pwd)) {
                        System.out.println("=========== 欢迎【用户 " + userId + " 】登陆成功==========");
                        // 进入二级菜单
                        while (loop) {
                            System.out.println("\n===========网络通讯系统二级菜单【用户 " + userId + "】==============");
                            System.out.println("\t\t\t 1 显示在线用户列表");
                            System.out.println("\t\t\t 2 群发消息");
                            System.out.println("\t\t\t 3 私聊消息");
                            System.out.println("\t\t\t 4 发送文件");
                            System.out.println("\t\t\t 9 退出系统");
                            System.out.println("------------------------------------------------");
                            System.out.println("请输入你的选择");

                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("请输入群聊内容：");
                                    String content = Utility.readString(200);
                                    messageClientService.sendMessageToAll(content, userId);
                                    break;
                                case "3":
                                    System.out.println("请输入想聊天的用户号(在线)：");
                                    String getterId = Utility.readString(20);
                                    System.out.println("请输入想说的话：");
                                    content = Utility.readString(200);
                                    messageClientService.sendMessageToOne(content, userId, getterId);
                                    break;
                                case "4":
                                    System.out.println("请输入接收者(在线用户):");
                                    getterId = Utility.readString(20);
                                    System.out.println("请输入发送文件的路径(形式如：d:\\ xx.jpg)");
                                    String src = Utility.readString(120);
                                    System.out.println("请输入把文件发送到接受者的接收路径(形式如：d:\\ xx.jpg)");
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
                        System.out.println("====== 登录失败 ========");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
