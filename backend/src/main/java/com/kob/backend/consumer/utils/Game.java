package com.kob.backend.consumer.utils;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


public class Game extends Thread{  //继承Thread类，就可以变成多线程的了
    private final Integer rows;  //长: 13  (|)
    private final Integer cols;  //宽: 14  (——)
    private final Integer wall_count;  //障碍物数量（墙的数量）
    private final static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};  //判断地图的连通性所用
    private final int[][] map;  //地图(一个二维数组)
    private final GamePlayer gamePlayerA, gamePlayerB;  //玩家A和B
    private Integer nextStepA = null, nextStepB = null;  //玩家A和B的下一步操作;0,1,2,3=>上下左右;为空表示没有下一步操作
    private final ReentrantLock lock = new ReentrantLock();  //多线程的锁
    private String status = "playing";  //游戏状态   playing -> finishing
    private String loser = null; //输者  all->平局 A->A输 B->B输
    public int[][] getMap() {  //获取地图
        return map;
    }
    public GamePlayer getPlayerA() {
        return gamePlayerA;
    }
    public GamePlayer getPlayerB() {
        return gamePlayerB;
    }
    public void SetNextStepA(Integer nextStepA) {
        lock.lock();  //上锁
        try {  //尝试读写数据
            this.nextStepA = nextStepA;
        } finally {  //不管读写数据会不会报错，都要解锁，不然会死锁
            lock.unlock();
        }

    }
    public void SetNextStepB(Integer nextStepB) {
        lock.lock();  //上锁
        try {  //尝试读写数据
            this.nextStepB = nextStepB;
        } finally {  //不管读写数据会不会报错，都要解锁，不然会死锁
            lock.unlock();
        }
    }

    public Game(Integer rows, Integer cols, Integer wall_count, Integer idA, Integer idB) {  //构造函数
        this.rows = rows;
        this.cols = cols;
        this.wall_count = wall_count;
        this.map = new int[rows][cols];
        gamePlayerA = new GamePlayer(idA, rows - 2, 1, new ArrayList<>());
        gamePlayerB = new GamePlayer(idB, 1, cols - 2, new ArrayList<>());
    }

    private boolean check( int sx, int sy, int tx, int ty) {  //判断地图的连通性（可以从地图的左下角走到右上角）
        if(sx == tx && sy == ty) return true;
        map[sx][sy] = 1;

        for(int i = 0; i < 4; i++) {
            int x = sx + dx[i];
            int y = sy + dy[i];
            if(x >= 0 && x < this.rows && y >= 0 && y < this.cols && map[x][y] == 0) {
                if(check(x, y, tx, ty)) {
                    map[sx][sy] = 0;
                    return true;
                }
            }
        }
        map[sx][sy] = 0;
        return false;
    }
    private boolean draw() {  //画地图
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                map[i][j] = 0;  //0表示空地，1表示墙体
            }
        }

        for(int i = 0; i < this.rows; i++){  //给上下边界加上墙
            map[i][0] = map[i][this.cols - 1] = 1;
        }

        for(int i = 0; i < this.cols; i++){  //给左右边界加上墙
            map[0][i] = map[this.rows - 1][i] = 1;
        }

        Random random = new Random();  //随机数

        for(int i = 0; i < this.wall_count / 2; i++){  //创建随机障碍物
            for(int j = 0; j < 1000; j++){
                int r = random.nextInt(this.rows);  //random.nextInt()产生0~1的随机数
                int c = random.nextInt(this.cols);
                if(map[r][c] == 1 || map[this.rows - 1 - r][this.cols - 1 - c] == 1) continue;  //保证不在重复的位置产生墙
                if(r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2) continue;  //保证左下和右上两个格子是没有墙的
                map[r][c] = 1;
                map[this.rows - 1 - r][this.cols - 1 - c] = 1;
                break;
            }
        }

        return check(this.rows - 2, 1, 1, this.cols - 2);
    }

    public void creatMap() {  //创建地图
        for(int i = 0; i < 1000; i++){  //尝试1000次，一定可以满足连通的条件
            if(draw()) break;
        }
    }

    private String getMapString() {  //将二维数组的地图转换为字符串，以便存入数据库(后面用来看回放)
        StringBuilder res = new StringBuilder();  //和String差不多
        for (int i = 0; i < rows; i ++ ) {
            for (int j = 0; j < cols; j ++ ) {
                res.append(map[i][j]);
            }
        }
        return res.toString();
    }
    private void saveToRecord() {  //存入对局记录到数据库里
        Record record = new Record(
                null,
                gamePlayerA.getId(),
                gamePlayerA.getSx(),
                gamePlayerA.getSy(),
                gamePlayerB.getId(),
                gamePlayerB.getSx(),
                gamePlayerB.getSy(),
                gamePlayerA.getStepsString(),
                gamePlayerB.getStepsString(),
                getMapString(),
                loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(record);
    }
    private boolean nextStep() {  //两名玩家的下一步操作
        try {
            Thread.sleep(200);  //防止有玩家在短时间内多次输入，前端会渲染不出来,200毫秒是因为前端设定设的速度是每秒走5格,走一格要200毫秒
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for(int i = 0; i < 50; i++){  //5秒后，若有玩家未输入则游戏结束
            if(i == 0) {  //前端的动画要2秒
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(100);  //睡100毫秒
                lock.lock();
                try {
                    if(nextStepA != null && nextStepB != null) {
                        getPlayerA().getSteps().add(nextStepA);
                        getPlayerB().getSteps().add(nextStepB);
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);  //报异常
                e.printStackTrace();  //直接输出
            }
        }
        return false;
    }

    private void SendAllMessage(String message) {  //向两名玩家发送信息（移动/游戏结果）
        if(WebSocketServer.users.get(gamePlayerA.getId()) != null)
            WebSocketServer.users.get(gamePlayerA.getId()).sendMessage(message);
        if(WebSocketServer.users.get(gamePlayerB.getId()) != null)
            WebSocketServer.users.get(gamePlayerB.getId()).sendMessage(message);
    }
    private void SendMove() {  //向两名玩家发送移动的信息
        lock.lock();
        try {
            JSONObject resp = new JSONObject();
            resp.put("event", "move");
            resp.put("A_direction", nextStepA);
            resp.put("B_direction", nextStepB);
            SendAllMessage(resp.toJSONString());
            nextStepA = nextStepB = null;  //清空操作信息
        } finally {
            lock.unlock();
        }
    }

    private void SendResult() {  //向两名玩家公布游戏结果
        JSONObject resp = new JSONObject();
        resp.put("event", "result");
        resp.put("loser", loser);
        saveToRecord();
        SendAllMessage(resp.toJSONString());
    }

    private boolean check_valid(List<Cell> cellsA, List<Cell> cellsB) {
        int n = cellsA.size();
        Cell cell = cellsA.get(n - 1);
        if (map[cell.x][cell.y] == 1) return false;
        for (int i = 0; i < n - 1; i ++ ) {
            if (cellsA.get(i).x == cell.x && cellsA.get(i).y == cell.y)
                return false;
        }
        for (int i = 0; i < n - 1; i ++ ) {
            if (cellsB.get(i).x == cell.x && cellsB.get(i).y == cell.y)
                return false;
        }
        return true;
    }

    private void judge() {  //判断两名玩家的操作是否合法
        List<Cell> cellsA = gamePlayerA.getCells();
        List<Cell> cellsB = gamePlayerB.getCells();

        boolean validA = check_valid(cellsA, cellsB);
        boolean validB = check_valid(cellsB, cellsA);
        if (!validA || !validB) {
            status = "finished";
            if (!validA && !validB) {
                loser = "all";
            } else if (!validA) {
                loser = "A";
            } else {
                loser = "B";
            }
        }
    }
    @Override
    public void run() {  //alt + insert键，重写方法  run()  入口函数
        for(int i = 0; i < 1000; i++) {  //在1000个回合内游戏肯定会结束
            if(nextStep()) {  //获取到了两名玩家的下一步操作
                judge();
                if(status.equals("playing")) {
                    SendMove();
                } else {
                    SendResult();
                    break;
                }
            } else {
                status = "finished";
                lock.lock();
                try {
                    if(nextStepA == null && nextStepB == null) {
                        loser = "all";
                    } else if(nextStepA == null) {
                        loser = "A";
                    } else {
                        loser = "B";
                    }
                } finally {
                    lock.unlock();
                }
                SendResult();
                break;
            }
        }
    }
}
