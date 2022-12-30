package com.kob.backend.consumer.utils;

import com.kob.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;

public class JwtAuthentication {
    public static Integer getUserId(String token) {  //jwt验证，如果能根据token解析出来userId，就表示合法，否则就是不合法;设为静态类型表示，我们希望可以不实例化此对象就调用此方法
        int userId = -1;  //初始值，表示用户不存在
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userId;
    }
}
