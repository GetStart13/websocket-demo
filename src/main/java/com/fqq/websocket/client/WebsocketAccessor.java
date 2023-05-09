package com.fqq.websocket.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

/**
 * <p> 2023/5/9 </p>
 * websocket 访问器
 *
 * @author Fqq
 */
public class WebsocketAccessor {
    private final MyWebsocketClient client;

    public WebsocketAccessor(String uri, Consumer<Object> callBack) throws URISyntaxException {
        client = new MyWebsocketClient(new URI(uri), callBack);
        // 测试客户端连接
        client.connect();
    }

    public void send(String text) {
        try {
            // 客户端实例锁，在连接时等待
            synchronized (client) {
                while (!client.isConnected()) {
                    client.wait();
                }
            }
            client.send(text);
            // 等待 2 秒后关闭连接
            /*Thread.sleep(2000L);
            client.getConnection().close();*/
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}