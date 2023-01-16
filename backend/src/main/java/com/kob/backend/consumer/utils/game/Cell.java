package com.kob.backend.consumer.utils.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
    public int x, y;  //表示蛇身的位置
}
