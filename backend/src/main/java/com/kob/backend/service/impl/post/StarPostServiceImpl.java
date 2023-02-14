package com.kob.backend.service.impl.post;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.post.PostMapper;
import com.kob.backend.mapper.post.StarMapper;
import com.kob.backend.pojo.post.Post;
import com.kob.backend.pojo.post.Star;
import com.kob.backend.service.post.StarPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarPostServiceImpl implements StarPostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private StarMapper starMapper;

    @Override
    public void starPost(Integer userId, Integer postId) {
        QueryWrapper<Star> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("post_id", postId);
        Star star = starMapper.selectOne(queryWrapper);  //查看用户是否已经收藏(防止用户连续点击)
        Post newPost = postMapper.selectById(postId);
        if(star == null){
            //收藏人数+1
            newPost.setStarCount(newPost.getStarCount() + 1);
            postMapper.updateById(newPost);

            //用户与帖子的关系绑定
            Star newStar = new Star(null, userId, postId);
            starMapper.insert(newStar);
        } else {
            //收藏人数-1
            newPost.setStarCount(newPost.getStarCount() - 1);
            postMapper.updateById(newPost);

            //用户与帖子的关系解除绑定
            starMapper.deleteById(star.getId());
        }
    }
}
