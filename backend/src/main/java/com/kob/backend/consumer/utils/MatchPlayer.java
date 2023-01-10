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
}
