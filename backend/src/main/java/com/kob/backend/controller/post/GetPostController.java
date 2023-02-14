package com.kob.backend.controller.post;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.service.post.GetPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GetPostController {
    @Autowired
    private GetPostService getPostService;

    @GetMapping("/post/getlist/")
    public List<JSONObject> getPosts(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));
        return getPostService.getPosts(userId);
    }
}
