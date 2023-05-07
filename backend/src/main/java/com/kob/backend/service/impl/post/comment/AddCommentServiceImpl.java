package com.kob.backend.service.impl.post.comment;

import com.kob.backend.mapper.post.PostMapper;
import com.kob.backend.mapper.post.comment.CommentLevel1Mapper;
import com.kob.backend.pojo.post.Post;
import com.kob.backend.pojo.post.comment.CommentLevel1;
import com.kob.backend.service.post.comment.AddCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddCommentServiceImpl implements AddCommentService {
    @Autowired
    private CommentLevel1Mapper commentLevel1Mapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public void addComment(Integer userId, Integer postId, String comment) {
        Date now = new Date();
        CommentLevel1 newCommentLevel1 = new CommentLevel1(null, userId, postId, comment, now, 0);
        Post post = postMapper.selectById(postId);
        post.setCommentCount(post.getCommentCount() + 1);
        postMapper.updateById(post);
        commentLevel1Mapper.insert(newCommentLevel1);
    }
}
