package com.kob.backend.service.post;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

public interface GetPostService {
    List<JSONObject> getPosts(Integer userId);
}
