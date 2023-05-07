package com.kob.backend.controller.post;

import com.kob.backend.service.post.LoveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoveUserController {
    @Autowired
    private LoveUserService loveUserService;

    @PostMapping("/api/post/loveuser/")
    public void loveUser(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));
        Integer byLoveUserId = Integer.parseInt(data.get("by_love_user_id"));
        loveUserService.loveUser(userId, byLoveUserId);
    }
}
