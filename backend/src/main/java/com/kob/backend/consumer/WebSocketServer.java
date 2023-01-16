package com.kob.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.utils.game.Game;
import com.kob.backend.consumer.utils.jwt.JwtAuthentication;
import com.kob.backend.consumer.utils.matchtool.MatchTool;
import com.kob.backend.mapper.BotMapper;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    // 线程安全的HashMap和Set
    public final static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();  //全局变量，对所有的实例化对象都可见，存储所有的链接
    private User user;  //储存用户信息，一旦匹配成功，将展示对手的头像和昵称
    public Game game = null;  //游戏
    private Session session = null;  //每个链接用session来维护
    public static UserMapper userMapper;
    public static RecordMapper recordMapper;

    public static BotMapper botMapper;
    @Autowired                                               //因为WebSocketServer
    public void setUserMapper(UserMapper userMapper) {       //不是单列模式
        WebSocketServer.userMapper = userMapper;             //所以不能用平常的注入方式
    }                                                        //要用一个set 方法，并设成全局变量
    @Autowired
    public void setRecordMapper(RecordMapper recordMapper) {
        WebSocketServer.recordMapper = recordMapper;
    }
    @Autowired
    public void setBotMapper(BotMapper botMapper) { WebSocketServer.botMapper = botMapper; }
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        Integer userId = JwtAuthentication.getUserId(token);  //JwtAuthentication是自己定义的一个工具类，用来解析token
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
            MatchTool.removePlayer(this.user.getId());  //一定要从匹配池里移除，防止因为有玩家因为直接关闭网页或者断网了，但仍然在匹配池里
        }
    }

    public static void startGame(User userA, User userB, Integer userABotId, Integer userBBotId) {
        Bot botA = botMapper.selectById(userABotId);
        Bot botB = botMapper.selectById(userBBotId);
        Game game = new Game(  //创建游戏
                13,
                14,
                20,
                userA.getId(),
                botA,
                userB.getId(),
                botB
        );
        game.creatMap();  //创建游戏地图
        if(users.get(userA.getId()) != null)
            users.get(userA.getId()).game = game;
        if(users.get(userB.getId()) != null)
            users.get(userB.getId()).game = game;

        game.start();  //多线程的开始    game里继承于Thread类的一个apl

        JSONObject Game = new JSONObject();  //地图信息
        Game.put("idA", game.getPlayerA().getId());
        Game.put("sxA", game.getPlayerA().getSx());
        Game.put("syA", game.getPlayerA().getSy());
        Game.put("idB", game.getPlayerB().getId());
        Game.put("sxB", game.getPlayerB().getSx());
        Game.put("syB", game.getPlayerB().getSy());
        Game.put("map", game.getMap());

        JSONObject respA = new JSONObject();  //匹配成功后展示对手的头像与昵称
        respA.put("status", "playing");
        respA.put("opponent_username", userB.getUsername());
        respA.put("opponent_photo", userB.getPhoto());
        respA.put("game", Game);
        if(users.get(userA.getId()) != null)
            users.get(userA.getId()).sendMessage(respA.toJSONString());  //发送信息给前端

        JSONObject respB = new JSONObject();  //匹配成功后展示对手的头像与昵称
        respB.put("status", "playing");
        respB.put("opponent_username", userA.getUsername());
        respB.put("opponent_photo", userA.getPhoto());
        respB.put("game", Game);
        if(users.get(userB.getId()) != null)
            users.get(userB.getId()).sendMessage(respB.toJSONString());  //发送信息给前端
    }
    private void startMatching(Integer botId) {
        System.out.println("start matching!");
        MatchTool.addPlayer(user.getId(), user.getRating(), botId);
    }

    private void stopMatching() {
        System.out.println("stop matching!");
        MatchTool.removePlayer(user.getId());
    }

    private void move(int direction) {  //云端的蛇移动
        if(game.getPlayerA().getId().equals(user.getId())) {  //判断是A玩家还是B玩家发送的消息
            if(game.getPlayerA().getBotId().equals(-1))  //亲自出马，才接受前端传过来的操作
                game.SetNextStepA(direction);
        } else if(game.getPlayerB().getId().equals(user.getId())){
            if(game.getPlayerB().getBotId().equals(-1))  //亲自出马，才接受前端传过来的操作
                game.SetNextStepB(direction);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {  //当做一个路由,message存储前端发送的信息
        System.out.println("receive message!");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)) {
            Integer botId = Integer.parseInt(data.getString("bot_id"));
            startMatching(botId);
        } else if("stop-matching".equals(event)){
            stopMatching();
        } else if("move".equals(event)) {
            move(data.getInteger("direction"));
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
                e.printStackTrace();  //将错误信息打印出来
            }
        }
    }
}
