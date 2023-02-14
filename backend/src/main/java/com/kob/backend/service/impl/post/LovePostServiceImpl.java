package com.kob.backend.service.impl.post;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.post.LoveMapper;
import com.kob.backend.mapper.post.PostMapper;
import com.kob.backend.pojo.post.Love;
import com.kob.backend.pojo.post.Post;
import com.kob.backend.service.post.LovePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LovePostServiceImpl implements LovePostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private LoveMapper loveMapper;

    @Override
    public void likePost(Integer userId, Integer postId) {
        QueryWrapper<Love> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("post_id", postId);
        Love love = loveMapper.selectOne(queryWrapper);  //查看用户是否已经赞过(防止用户连续点击)
        if(love == null){
            //点赞人数+1
            Post newPost = postMapper.selectById(postId);
            newPost.setLoveCount(newPost.getLoveCount() + 1);
            postMapper.updateById(newPost);

            //用户与帖子的关系绑定
            Love newLove = new Love(null, userId, postId);
            loveMapper.insert(newLove);
        } else {
            //点赞人数-1
            Post newPost = postMapper.selectById(postId);
            newPost.setLoveCount(newPost.getLoveCount() - 1);
            postMapper.updateById(newPost);

            //用户与帖子的关系解除绑定
            loveMapper.deleteById(love.getId());
        }
    }
}
