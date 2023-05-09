package com.fqq.websocket.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.function.Consumer;

/**
 * <p> 2023/5/9 </p>
 * websocket 客户端
 *
 * @author Fqq
 */
public class MyWebsocketClient extends WebSocketClient {
    private boolean connected;
    private final Consumer<Object> callBack;

    public boolean isConnected() {
        return connected;
    }

    public MyWebsocketClient(URI serverUri, Consumer<Object> callBack) {
        super(serverUri);
        this.callBack = callBack;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("开启了连接。。。");
        this.connected = true;
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void onMessage(String message) {
        callBack.accept(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("关闭了连接。。。");
    }

    @Override
    public void onError(Exception exception) {
        exception.printStackTrace();
    }
}
