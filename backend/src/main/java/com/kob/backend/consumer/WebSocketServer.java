package com.kob.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    // 线程安全的HashMap和Set
    final private static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();  //全局变量，对所有的实例化对象都可见，存储所有的链接
    final private static CopyOnWriteArraySet<User> matchTool= new CopyOnWriteArraySet<>();  //匹配池
    private User user;  //储存用户信息，一旦匹配成功，将展示对手的头像和昵称

    private Game game = null;  //游戏地图
    private Session session = null;  //每个链接用session来维护

    private static UserMapper userMapper;                    //因为WebSocketServer
    @Autowired                                               //不是单列模式
    public void setUserMapper(UserMapper userMapper) {       //所以不能用平常的注入方式
        WebSocketServer.userMapper = userMapper;             //要用一个set 方法，并设成全局变量
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if(userId != -1) {
            users.put(userId, this);
            System.out.println("connect!");
        } else {
            this.session.close();
        }
    }

    @OnClose
    public void onClose() {
        System.out.println("disconnect!");
        if(this.user != null) {
            users.remove(this.user.getId());
            matchTool.remove(this.user);
        }
    }

    private void startMatching() {
        System.out.println("start matching!");
        matchTool.add(this.user);

        while(matchTool.size() >= 2) {
            Iterator<User> it = matchTool.iterator();  //迭代器
            User userA = it.next();
            User userB = it.next();

            matchTool.remove(userA);
            matchTool.remove(userB);

            Game game = new Game(13, 13, 20);
            game.creatMap();  //创建游戏地图

            JSONObject respA = new JSONObject();
            JSONObject respB = new JSONObject();

            respA.put("status", "playing");
            respA.put("opponent_username", userB.getUsername());
            respA.put("opponent_photo", userB.getPhoto());
            respA.put("gameMap", game.getMap());
            users.get(userA.getId()).sendMessage(respA.toJSONString());

            respB.put("status", "playing");
            respB.put("opponent_username", userA.getUsername());
            respB.put("opponent_photo", userA.getPhoto());
            respB.put("gameMap", game.getMap());
            users.get(userB.getId()).sendMessage(respB.toJSONString());
        }
    }

    private void stopMatching() {
        System.out.println("stop matching!");
        matchTool.remove(this.user);
    }

    @OnMessage
    public void onMessage(String message, Session session) {  //当做一个路由,message存储前端发送的信息
        System.out.println("receive message!");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)) {
            startMatching();
        } else if("stop-matching".equals(event)){
            stopMatching();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) {  //后端向前端(当前链接)发送信息
        synchronized (this.session) {  //加一个锁
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
