package com.kob.backend.controller.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.service.ranklist.GetRankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ranklist {
    @Autowired
    private GetRankListService getRankListService;

    @GetMapping("/api/ranklist/getlist/")
    public JSONObject getRankList(Integer page, Integer userId) {
        return getRankListService.getRankList(page);
    }
}
