package com.kob.backend.service.impl.ranklist;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.ranklist.GetRankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRankListServiceImpl implements GetRankListService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getRankList(Integer page) {
        IPage<User> userIPage = new Page<>(page, 8);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");

        JSONObject resp = new JSONObject();
        List<User> users = userMapper.selectPage(userIPage, queryWrapper).getRecords();
        long user_count = userMapper.selectCount(null);
        for(User user : users) {
            user.setPassword("");  //将密码清除
        }
        resp.put("users", users);
        resp.put("user_count", user_count);
        return resp;
    }
}
