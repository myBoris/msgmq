package com.msgmq.app;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public abstract class MyWebSocketService {
    private static Map<String, Session> clients = new ConcurrentHashMap<>();


    public abstract void sendTo(String message);
    @OnOpen
    public  void onOpen(Session session){
        clients.put(session.getId(), session);
    }

    // 客户端关闭
    @OnClose
    public  void onClose(Session session){
        clients.remove(session.getId());
    }

    // 发生错误
    @OnError
    public  void onError(Session session,Throwable throwable){
        throwable.printStackTrace();
        if (clients.get(session.getId())!=null){
            clients.remove(session.getId());
        }
    }

    // 收到消息
    @OnMessage
    public  void onMessage(String message){
        this.sendTo(message);
    }

    // 消息
    //private void sendTo(String message){
    //    for (Map.Entry<String,Session> sessionEntry : clients.entrySet()){
    //        sessionEntry.getValue().getAsyncRemote().sendText(message);
    //    }
    //}
}
