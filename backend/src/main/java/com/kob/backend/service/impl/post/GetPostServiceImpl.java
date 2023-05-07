package com.kob.backend.service.impl.post;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.mapper.post.LovePostMapper;
import com.kob.backend.mapper.post.PostMapper;
import com.kob.backend.mapper.post.StarMapper;
import com.kob.backend.mapper.post.comment.CommentLevel1Mapper;
import com.kob.backend.mapper.post.comment.CommentLevel2Mapper;
import com.kob.backend.mapper.post.comment.LoveCommentLevel1Mapper;
import com.kob.backend.mapper.post.comment.LoveCommentLevel2Mapper;
import com.kob.backend.pojo.User;
import com.kob.backend.pojo.post.LovePost;
import com.kob.backend.pojo.post.Post;
import com.kob.backend.pojo.post.Star;
import com.kob.backend.pojo.post.comment.CommentLevel1;
import com.kob.backend.pojo.post.comment.CommentLevel2;
import com.kob.backend.pojo.post.comment.LoveCommentLevel1;
import com.kob.backend.pojo.post.comment.LoveCommentLevel2;
import com.kob.backend.service.post.GetPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GetPostServiceImpl implements GetPostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LovePostMapper loveMapper;
    @Autowired
    private StarMapper starMapper;
    @Autowired
    private CommentLevel1Mapper commentLevel1Mapper;
    @Autowired
    private CommentLevel2Mapper commentLevel2Mapper;
    @Autowired
    private LoveCommentLevel1Mapper loveCommentLevel1Mapper;
    @Autowired
    private LoveCommentLevel2Mapper loveCommentLevel2Mapper;
    private Date nowTime;  //当用户请求所有帖子信息时的当前时间
    public GetPostServiceImpl() {
        this.nowTime = new Date();
    }
    private String getCreateTime(Date createTime) {
        long timeDiff = (nowTime.getTime() - createTime.getTime()) / 1000;  //秒为单位
        String time = "秒";
        if(timeDiff < 0) {  //防止出现意外
            timeDiff = 52;
        } else if(timeDiff / 31536000 > 0) {
            timeDiff = timeDiff / 31536000;
            time = "年";
        } else if(timeDiff / 86400 > 0) {
            timeDiff = timeDiff / 86400;
            time = "天";
        } else if(timeDiff / 3600 > 0) {
            timeDiff = timeDiff / 3600;
            time = "小时";
        } else if(timeDiff / 60 > 0) {
            timeDiff = timeDiff / 60;
            time = "分钟";
        }
        return timeDiff + time + "前";
    }
    @Override
    public List<JSONObject> getPosts(Integer userId, Integer showPostCount) {
        //每个json封装一个帖子的所有内容
        //一个json里有该条帖子的所有信息+一个list<JSONObject> commentLevel1s(所有的一级评论)
        //commentLevel1里的一个json封装了一个一级评论的所有信息+一个list<JSONObject> commentLevel2s(所有的二级评论)
        //commentLevel2里的一个json封装了一个二级评论的所有信息

        List<JSONObject> posts = new ArrayList<>();  //所有的帖子及其评论

        //取出所有的帖子，并根据创建时间排倒序
        QueryWrapper<Post> queryWrapperPost = new QueryWrapper<>();
        List<Post> orPosts = postMapper.selectList(queryWrapperPost.orderByDesc("create_time"));

        for(Post orpost : orPosts) {
            //该帖子的阅读量++
            Post newPost = postMapper.selectById(orpost.getId());
            newPost.setReadCount(newPost.getReadCount() + 1);
            postMapper.updateById(newPost);
            //第一部分：处理该条帖子的所有信息。
            JSONObject post = new JSONObject();
            User user1 = userMapper.selectById(orpost.getUserId());  //该用户的信息
            post.put("id", orpost.getId());
            post.put("user_id", orpost.getUserId());
            post.put("username", user1.getUsername());
            post.put("photo", user1.getPhoto());
            post.put("content", orpost.getContent());
            post.put("comment_count", orpost.getCommentCount());
            post.put("love_count", orpost.getLoveCount());
            post.put("read_count", orpost.getReadCount());
            post.put("star_count", orpost.getStarCount());
            post.put("create_time", getCreateTime(orpost.getCreateTime()));
            //判断该用户是否点赞过该条帖子。
            String isLove1 = "yes";

            QueryWrapper<LovePost> queryWrapperLovePost = new QueryWrapper<>();
            List<LovePost> lovePosts = loveMapper.selectList(queryWrapperLovePost.eq("user_id", userId).eq("post_id", orpost.getId()));
            if(lovePosts.size() == 0) {
                isLove1 = "no";
            }
            post.put("is_love", isLove1);
            //判断该用户是否收藏过该条帖子
            String isStar = "yes";
            QueryWrapper<Star> queryWrapperStar = new QueryWrapper<>();
            List<Star> stars = starMapper.selectList(queryWrapperStar.eq("user_id", userId).eq("post_id", orpost.getId()));
            if(stars.size() == 0) {
                isStar = "no";
            }
            post.put("is_star", isStar);

            //第二部分：处理该条帖子的所有一级评论
            List<JSONObject> commentLevel1s = new ArrayList<>();
            QueryWrapper<CommentLevel1> queryWrapperCommentLevel1 = new QueryWrapper<>();
            List<CommentLevel1> orCommentLevel1s = commentLevel1Mapper.selectList(queryWrapperCommentLevel1.eq("post_id", orpost.getId()));
            for(CommentLevel1 orCommentLevel1 : orCommentLevel1s) {
                JSONObject commentLevel1 = new JSONObject();
                User user2 = userMapper.selectById(orCommentLevel1.getUserId());  //该一级评论的发出者的信息
                commentLevel1.put("id", orCommentLevel1.getId());
                commentLevel1.put("post_id", orCommentLevel1.getPostId());
                commentLevel1.put("user_id", orCommentLevel1.getUserId());
                commentLevel1.put("username", user2.getUsername());
                commentLevel1.put("photo", user2.getPhoto());
                commentLevel1.put("content", orCommentLevel1.getContent());
                commentLevel1.put("love_count", orCommentLevel1.getLoveCount());
                commentLevel1.put("level", "1");
                commentLevel1.put("create_time", getCreateTime(orCommentLevel1.getCreateTime()));
                //判断该用户是否点赞了该条帖子的该条一级评论
                String isLove2 = "yes";
                QueryWrapper<LoveCommentLevel1> queryWrapperLoveCommentLevel1 = new QueryWrapper<>();
                List<LoveCommentLevel1> loveCommentLevel1s = loveCommentLevel1Mapper.selectList(queryWrapperLoveCommentLevel1.eq("user_id", userId).eq("comment_id", orCommentLevel1.getId()));
                if(loveCommentLevel1s.size() == 0) {
                    isLove2 = "no";
                }
                commentLevel1.put("is_love", isLove2);

                //第三部分：处理该条一级评论的所有二级评论
                List<JSONObject> commentLevel2s = new ArrayList<>();
                QueryWrapper<CommentLevel2> queryWrapperCommentLevel2 = new QueryWrapper<>();
                List<CommentLevel2> orCommentLevel2s = commentLevel2Mapper.selectList(queryWrapperCommentLevel2.eq("belong_comment_id", orCommentLevel1.getId()));
                for(CommentLevel2 orCommentLevel2 : orCommentLevel2s) {
                    JSONObject commentLevel2 = new JSONObject();
                    User user3 = userMapper.selectById(orCommentLevel2.getUserId());
                    commentLevel2.put("id", orCommentLevel2.getId());
                    commentLevel2.put("user_id", orCommentLevel2.getUserId());
                    commentLevel2.put("username", user3.getUsername());
                    commentLevel2.put("photo", user3.getPhoto());
                    commentLevel2.put("content", orCommentLevel2.getContent());
                    commentLevel2.put("level", "2");
                    commentLevel2.put("create_time", getCreateTime(orCommentLevel2.getCreateTime()));
                    commentLevel2.put("love_count", orCommentLevel2.getLoveCount());
                    String isLove3 = "yes";
                    QueryWrapper<LoveCommentLevel2> queryWrapperLoveCommentLevel2 = new QueryWrapper<>();
                    List<LoveCommentLevel2> loveCommentLevel2s = loveCommentLevel2Mapper.selectList(queryWrapperLoveCommentLevel2.eq("user_id", userId).eq("comment_id", orCommentLevel2.getId()));
                    if(loveCommentLevel2s.size() == 0) {
                        isLove3 = "no";
                    }
                    commentLevel2.put("is_love", isLove3);
                    commentLevel2s.add(commentLevel2);
                }
                commentLevel1.put("comment_leve2s", commentLevel2s);
                commentLevel1s.add(commentLevel1);
            }
            post.put("comment_level1s", commentLevel1s);
            posts.add(post);
        }
        return posts;
    }
}
