package com.fqq.websocket.client;


import java.net.URISyntaxException;
import java.util.function.Consumer;

public class ClientDemo {
    public static void main(String[] args) {
        ClientDemo demo = new ClientDemo();
        try {
            demo.postHandler(v -> System.out.println("拿到响应数据：" + v));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    void postHandler(Consumer<Object> callBack) throws URISyntaxException {
        WebsocketAccessor accessor = new WebsocketAccessor("ws://127.0.0.1:8666",
                result -> {
                    // callBack 地址一样，说明 lambda 表达式内部调用到参数时，如果 lambda 对象未被垃圾回收，该参数会一直保持存活状态
                    System.out.println(callBack);
                    System.out.println("对数据做一些后置处理：" + result);
                    callBack.accept(result);
                }
        );
        accessor.send("some data");
    }
}
