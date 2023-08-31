package com.zrd.service;

import com.zrd.common.Message;
import com.zrd.common.MessageType;
import com.zrd.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class SendNewsToAllService implements Runnable {

    @Override
    public void run() {
        // 为了多次推送新闻
        while (true) {
            System.out.println("请输入服务器所要推送的消息/消息[输入 exit 表示退出推送服务]");
            String news = Utility.readString(200);
            if ("exit".equals(news)) {
                break;
            }
            // 构建消息
            Message message = new Message();
            message.setSender("服务器");
            message.setMessType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("服务器推送消息给所有人 说: " + news);
            // 遍历当前所有通信线程
            HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onLineUserId = iterator.next();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(
                            hm.get(onLineUserId).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
