package com.kob.backend.controller.post;

import com.kob.backend.service.post.StarPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StarPostController {
    @Autowired
    private StarPostService starPostService;

    @PostMapping("/post/starpost/")
    public void starPost(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));
        Integer postId = Integer.parseInt(data.get("post_id"));
        starPostService.starPost(userId, postId);
    }
}
