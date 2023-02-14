package com.kob.backend.service.impl.post;

import com.kob.backend.mapper.post.PostMapper;
import com.kob.backend.pojo.post.Post;
import com.kob.backend.service.post.AddPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddPostServiceImpl implements AddPostService {
    @Autowired
    private PostMapper postMapper;

    @Override
    public void addPost(Integer userId, String content) {
        Date now = new Date();
        Post post = new Post(null, userId, content, now, 0, 0, 0);
        postMapper.insert(post);
    }
}
