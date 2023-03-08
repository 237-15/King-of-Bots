package com.kob.backend.service.impl.post.comment;

import com.kob.backend.mapper.post.comment.CommentMapper;
import com.kob.backend.pojo.post.comment.Comment;
import com.kob.backend.service.post.comment.AddCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AddCommentServiceImpl implements AddCommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addComment(Integer userId, Integer postId, String comment) {
        Comment newComment = new Comment(null, userId, postId, comment);
        commentMapper.insert(newComment);
    }
}
