package com.kob.backend.service.impl.post;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.post.LoveMapper;
import com.kob.backend.mapper.post.PostMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.mapper.post.StarMapper;
import com.kob.backend.pojo.post.Love;
import com.kob.backend.pojo.post.Post;
import com.kob.backend.pojo.User;
import com.kob.backend.pojo.post.Star;
import com.kob.backend.service.post.GetPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class GetPostServiceImpl implements GetPostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoveMapper loveMapper;
    @Autowired
    private StarMapper starMapper;
    @Override
    public List<JSONObject> getPosts(Integer userId) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<Post> posts = postMapper.selectList(queryWrapper);  //每条帖子的详细内容

        QueryWrapper<Love> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", userId);
        List<Love> loves = loveMapper.selectList(queryWrapper1);

        QueryWrapper<Star> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", userId);
        List<Star> stars = starMapper.selectList(queryWrapper2);

        Date now = new Date();
        List<JSONObject> resp = new ArrayList<>();  //每条帖子用一个json封装(包括用户的用户名和头像)
        for(Post post : posts) {
            User user = userMapper.selectById(post.getUserId());

            boolean isLove = false;
            for(Love love : loves) {
                if(Objects.equals(love.getPostId(), post.getId())) {
                    isLove = true;
                    break;
                }
            }

            boolean isStar = false;
            for(Star star : stars) {
                if(Objects.equals(star.getPostId(), post.getId())) {
                    isStar = true;
                    break;
                }
            }

            long timeDiff = (now.getTime() - post.getCreateTime().getTime()) / 1000;
            String ydhms = "秒";
            if(timeDiff < 0) {  //防止出现意外
                timeDiff = 52;
            } else if(timeDiff / 31536000 > 0) {
                timeDiff = timeDiff / 31536000;
                ydhms = "年";
            } else if(timeDiff / 86400 > 0) {
                timeDiff = timeDiff / 86400;
                ydhms = "天";
            } else if(timeDiff / 3600 > 0) {
                timeDiff = timeDiff / 3600;
                ydhms = "小时";
            } else if(timeDiff / 60 > 0) {
                timeDiff = timeDiff / 60;
                ydhms = "分钟";
            }

            JSONObject item = new JSONObject();
            item.put("username", user.getUsername());
            item.put("photo", user.getPhoto());
            item.put("isLove", isLove);
            item.put("isStar", isStar);
            item.put("timeDiff", timeDiff);
            item.put("ydhms", ydhms);
            item.put("post", post);
            resp.add(item);
        }
        return resp;
    }
}
