package com.kob.backend.service.impl.post.comment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.post.comment.CommentLevel1Mapper;
import com.kob.backend.mapper.post.comment.CommentLevel2Mapper;
import com.kob.backend.mapper.post.comment.LoveCommentLevel1Mapper;
import com.kob.backend.mapper.post.comment.LoveCommentLevel2Mapper;
import com.kob.backend.pojo.post.comment.CommentLevel1;
import com.kob.backend.pojo.post.comment.CommentLevel2;
import com.kob.backend.pojo.post.comment.LoveCommentLevel1;
import com.kob.backend.pojo.post.comment.LoveCommentLevel2;
import com.kob.backend.service.post.comment.LoveCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoveCommentServiceImpl implements LoveCommentService {
    @Autowired
    private LoveCommentLevel1Mapper loveCommentLevel1Mapper;
    @Autowired
    private LoveCommentLevel2Mapper loveCommentLevel2Mapper;
    @Autowired
    private CommentLevel1Mapper commentLevel1Mapper;
    @Autowired
    private CommentLevel2Mapper commentLevel2Mapper;
    @Override
    public void loveComment(Integer id, Integer userId, Integer postId, Integer commentId, Integer level) {
        if(level.equals(1)) {
            CommentLevel1 commentLevel1 = commentLevel1Mapper.selectById(id);
            QueryWrapper<LoveCommentLevel1> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId).eq("comment_id", id);
            LoveCommentLevel1 loveCommentLevel1 = loveCommentLevel1Mapper.selectOne(queryWrapper);  //查看用户是否已经赞过(防止用户连续点击)
            if(loveCommentLevel1 == null){
                //点赞人数+1
                commentLevel1.setLoveCount(commentLevel1.getLoveCount() + 1);
                commentLevel1Mapper.updateById(commentLevel1);

                //用户与评论的关系绑定
                LoveCommentLevel1 newloveCommentLevel1 = new LoveCommentLevel1(null, userId, postId, id);
                loveCommentLevel1Mapper.insert(newloveCommentLevel1);
            } else {
                //点赞人数-1
                commentLevel1.setLoveCount(commentLevel1.getLoveCount() - 1);
                commentLevel1Mapper.updateById(commentLevel1);

                //用户与帖子的关系解除绑定
                loveCommentLevel1Mapper.deleteById(loveCommentLevel1.getId());
            }
        } else if(level.equals(2)){
            CommentLevel2 commentLevel2 = commentLevel2Mapper.selectById(id);
            QueryWrapper<LoveCommentLevel2> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId).eq("comment_id", commentId);
            LoveCommentLevel2 loveCommentLevel2 = loveCommentLevel2Mapper.selectOne(queryWrapper);  //查看用户是否已经赞过(防止用户连续点击)
            if(loveCommentLevel2 == null){
                //点赞人数+1
                commentLevel2.setLoveCount(commentLevel2.getLoveCount() + 1);
                commentLevel2Mapper.updateById(commentLevel2);

                //用户与评论的关系绑定
                LoveCommentLevel2 newloveCommentLevel2 = new LoveCommentLevel2(null, userId, postId, id);
                loveCommentLevel2Mapper.insert(newloveCommentLevel2);
            } else {
                //点赞人数-1
                commentLevel2.setLoveCount(commentLevel2.getLoveCount() - 1);
                commentLevel2Mapper.updateById(commentLevel2);

                //用户与帖子的关系解除绑定
                loveCommentLevel2Mapper.deleteById(loveCommentLevel2.getId());
            }
        }
    }
}
