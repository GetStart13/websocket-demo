package com.fqq.websocket.server;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/")
@Component
public class WebsocketServer {

    private Session session;

    // 连接时执行
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("打开了连接。。。");
    }

    // 关闭时执行
    @OnClose
    public void onClose() {
        System.out.println("关闭了连接。。。");
    }

    // 收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("传过来的消息是: " + message);
        int i = 5;
        // 不停地响应数据
        while (i >= 0) {
            this.session.getBasicRemote().sendText("我推送了数据：" + message);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            --i;
        }
    }

    // 连接错误时执行
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
