package com.kob.backend.service.post.comment;

import java.util.Map;

public interface AddCommentService {
    void addComment(Integer userId, Integer postId, String comment);
}
