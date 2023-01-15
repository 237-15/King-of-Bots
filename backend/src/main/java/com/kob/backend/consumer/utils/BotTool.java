package com.kob.backend.consumer.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BotTool extends Thread {
    private final static ReentrantLock lock = new ReentrantLock();
    private final static Condition condition = lock.newCondition();  //条件变量
    private static Queue<Bot> Bots = new LinkedList<>();

    public static void addBot(Integer userId, String botCode, String mapInfo) {
        lock.lock();
        try {
            Bot bot = new Bot(userId, botCode, mapInfo);
            Bots.add(bot);
            condition.signalAll();  //  起来工作
        }finally {
            lock.unlock();
        }
    }

    private void consume(Bot bot) {
        BotConsumer botConsumer = new BotConsumer();  //为每个bot代码的执行开一个单独的线程
        botConsumer.startTimeOut(2000, bot);  //执行该bot的代码，并且最多等待这个bot2秒
    }

    @Override
    public void run() {
        while(true) {
            lock.lock();
            if (Bots.isEmpty()) {  //队列为空
                try {
                    condition.await();  //使该线程睡住，休眠,并且此函数执行后会自动解锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock();
                    break;
                }
            } else {
                Bot bot = Bots.remove();  //删除并返回
                lock.unlock();
                consume(bot);
            }
        }
    }
}
