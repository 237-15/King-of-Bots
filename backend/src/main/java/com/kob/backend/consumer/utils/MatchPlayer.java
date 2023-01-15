package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchPlayer {
    private Integer id;
    private Integer rating;
    private Integer waitingTime;
    private Integer botId;  //表示玩家是通过那个bot出战的，-1表示亲自出马
}
