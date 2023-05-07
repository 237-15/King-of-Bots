package com.kob.backend.service.impl.post;

import com.kob.backend.mapper.post.LoveUserMapper;
import com.kob.backend.pojo.post.LoveUser;
import com.kob.backend.service.post.LoveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoveUserServiceImpl implements LoveUserService {
    @Autowired
    private LoveUserMapper loveUserMapper;
    @Override
    public void loveUser(Integer userId, Integer byLoveUserId) {
        LoveUser loveUser = new LoveUser(null, userId, byLoveUserId);
        loveUserMapper.insert(loveUser);
    }
}
