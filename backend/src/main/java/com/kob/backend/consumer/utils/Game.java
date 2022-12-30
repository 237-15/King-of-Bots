package com.kob.backend.consumer.utils;

import java.util.Random;

public class Game {
    final private Integer rows;  //长: 13
    final private Integer cols;  //宽: 13
    final private Integer wall_count;  //障碍物数量（墙的数量）
    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};  //判断地图的连通性
    final private int[][] map;  //地图

    private Random random = new Random();  //随机数

    public Game(Integer rows, Integer cols, Integer wall_count) {
        this.rows = rows;
        this.cols = cols;
        this.wall_count = wall_count;
        this.map = new int[rows][cols];
    }

    public int[][] getMap() {  //获取地图
        return map;
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

        for(int i = 0; i < this.wall_count / 2; i++){  //创建随机障碍物
            for(int j = 0; j < 1000; j++){
                int r = random.nextInt(this.rows);  //random.nextInt()产生0~1的随机数
                int c = random.nextInt(this.cols);
                if(map[r][c] == 1 || map[this.rows - 1 - r][this.cols - 1 - c] == 1) continue;  //保证不在重复的位置产生墙
                if(r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2) continue;  //保证左下和右上两个格子是没有墙的
                map[r][c] = map[this.rows - 1 - r][this.cols - 1 - r] = 1;
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
}
