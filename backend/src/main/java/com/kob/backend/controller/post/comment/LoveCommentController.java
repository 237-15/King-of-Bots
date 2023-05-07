package com.kob.backend.controller.post.comment;

import com.kob.backend.service.post.comment.LoveCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoveCommentController {
    @Autowired
    private LoveCommentService loveCommentService;
    @PostMapping("/api/post/comment/love/")
    public void LoveComment(@RequestParam Map<String, String> data) {
        Integer id = Integer.parseInt(data.get("id"));
        Integer userId = Integer.parseInt(data.get("user_id"));
        Integer level = Integer.parseInt(data.get("level"));
        int postId = -1;
        int commentId = -1;
        if(level.equals(1)) {
            postId = Integer.parseInt(data.get("post_id"));
        } else if(level.equals(2)){
            commentId = Integer.parseInt(data.get("comment_id"));
        }
        loveCommentService.loveComment(id, userId, postId, commentId, level);
    }
}
