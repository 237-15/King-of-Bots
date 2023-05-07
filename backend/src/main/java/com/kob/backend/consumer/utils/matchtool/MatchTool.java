package com.kob.backend.consumer.utils.matchtool;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchTool extends Thread{
    private static List<MatchPlayer> matchTool = new ArrayList<>();  //匹配池
    private final static ReentrantLock lock = new ReentrantLock();

    public static void addPlayer(Integer id, Integer rating, Integer botId) {
        System.out.println("add Player: " + id + " " + rating);
        lock.lock();
        try {
            MatchPlayer matchPlayer = new MatchPlayer(id, rating, 0, botId);
            matchTool.add(matchPlayer);
        }finally {
            lock.unlock();
        }
    }

    public static void removePlayer(Integer id) {
        System.out.println("remove Player " + id);
        lock.lock();
        try {
            List<MatchPlayer> newMatchTool = new ArrayList<>();
            for(MatchPlayer matchPlayer : matchTool) {
                if(!matchPlayer.getId().equals(id)) {
                    newMatchTool.add(matchPlayer);
                }
            }
            matchTool = newMatchTool;
        }finally {
            lock.unlock();
        }
    }

    private void increaseWaitingTime() {  // 将所有当前玩家的等待时间加1
        for (MatchPlayer player: matchTool) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }

    private boolean check(MatchPlayer PlayerA, MatchPlayer PlayerB) {  //检查玩家A和B是否可以匹配成功
        int ratingDelta = Math.abs(PlayerA.getRating() - PlayerB.getRating());
        int waitingTime = Math.min(PlayerA.getWaitingTime(), PlayerB.getWaitingTime());
        return ratingDelta <= waitingTime * 10;
    }

    private void matchPlayer() {  //尝试匹配所有人
//        System.out.println("match players: " + matchTool.toString());
        boolean[] used = new boolean[matchTool.size()];  //表示该玩家有没有匹配成功
        for(int i = 0; i < matchTool.size(); i++){
            if(used[i]) continue;  //如果已经匹配成功就看看下一个玩家
            for(int j = i + 1; j < matchTool.size(); j++){
                if(used[j]) continue;  //如果已经匹配成功就看看下一个玩家
                MatchPlayer PlayerA = matchTool.get(i);
                MatchPlayer PlayerB = matchTool.get(j);
                User userA = WebSocketServer.userMapper.selectById(PlayerA.getId());
                User userB = WebSocketServer.userMapper.selectById(PlayerB.getId());
                if(check(PlayerA, PlayerB)) {
                    used[i] = used[j] = true;
                    removePlayer(PlayerA.getId());
                    removePlayer(PlayerB.getId());
                    WebSocketServer.startGame(userA, userB, PlayerA.getBotId(), PlayerB.getBotId());
                    break;
                }
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);  //每过一秒看看是否有玩家能匹配成功
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchPlayer();
                }finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
