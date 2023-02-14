package com.kob.backend.controller.post;

import com.kob.backend.service.post.AddPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddPostController {
    @Autowired
    private AddPostService addPostService;

    @PostMapping("/post/addpost/")
    public void addPost(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));
        String content = data.get("content");
        addPostService.addPost(userId, content);
    }
}
