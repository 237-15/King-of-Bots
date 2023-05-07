package com.kob.backend.service.post.comment;

public interface LoveCommentService {
    void loveComment(Integer id, Integer userId, Integer postId, Integer commentId, Integer level);
}
