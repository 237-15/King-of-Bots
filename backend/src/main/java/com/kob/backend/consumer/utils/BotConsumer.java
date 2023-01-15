package com.kob.backend.consumer.utils;

import com.kob.backend.consumer.WebSocketServer;
import org.joor.Reflect;

import java.util.UUID;

public class BotConsumer extends Thread{
    private Bot bot;

    public void startTimeOut(Integer timeOut, Bot bot) {
        this.bot = bot;
        this.start();

        try {
            this.join(timeOut);  //表示等待run方法里的代码运行，最多等待timeOut秒，finally会执行interrupt中断此线程，防止玩家的bot中有死循环,卡死整个线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();  //中断当前线程
        }
    }

    private String addUid(String code, String uid) {  // 在code中的Bot类名后添加uid
        int k = code.indexOf(" implements com.kob.backend.consumer.utils.BotCodeInterface");
        return code.substring(0, k) + uid + code.substring(k);
    }

    @Override
    public void run() {
        UUID uuid = UUID.randomUUID();  //每次返回一个不一样ID
        String uid = uuid.toString().substring(0, 8);  //substring就是取出字符串的0至7位字符(左闭右开)

        BotCodeInterface botCodeInterface = Reflect.compile(  //Reflect是joor包里的,可以动态的执行一段java代码
            "com.kob.backend.consumer.utils.BotCode" + uid,
             addUid(bot.getBotCode(), uid)
        ).create().get();

        Integer direction = botCodeInterface.nextMove(bot.getMapInfo());

        Game game = WebSocketServer.users.get(bot.getUserId()).game;
        if(game != null) {
            if(game.getPlayerA().getId().equals(bot.getUserId())) {  //判断是A玩家还是B玩家发送的消息
                game.SetNextStepA(direction);
            } else if(game.getPlayerB().getId().equals(bot.getUserId())){
                game.SetNextStepB(direction);
            }
        }

        System.out.println("move: id=" + bot.getUserId() + " direction=" + direction);
    }
}
