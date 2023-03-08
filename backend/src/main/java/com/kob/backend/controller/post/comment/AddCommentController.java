package com.kob.backend.controller.post.comment;

import com.kob.backend.service.post.comment.AddCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddCommentController {
    @Autowired
    private AddCommentService addCommentService;

    @PostMapping("/post/comment/add/")
    public void addComment(@RequestParam Map<String, String> data) {
        Integer userId = Integer.parseInt(data.get("user_id"));
        Integer postId = Integer.parseInt(data.get("post_id"));
        String comment = data.get("comment");
        System.out.println("&" +  comment + "&");
        addCommentService.addComment(userId, postId, comment);
    }
}
