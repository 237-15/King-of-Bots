package com.kob.backend.controller.post;

import com.kob.backend.service.post.LovePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LikePostController {
    @Autowired
    private LovePostService lovePostService;

    @PostMapping("/post/likepost/")
    public void likePost(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));  //用户id
        Integer postId = Integer.parseInt(data.get("post_id"));  //用户点赞的帖子id
        lovePostService.likePost(userId, postId);
    }
}
