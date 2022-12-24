package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveServiceImpl implements RemoveService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Integer bot_id = Integer.parseInt(data.get("bot_id"));  //因为传过来的数据是字符串，所以要变成整数]
        Bot bot = botMapper.selectById(bot_id);  //通过bot_id找到相应的bot
        Map<String, String> map = new HashMap<>();

        if(bot_id == 0) {
            map.put("error_message", "bot不存在或已被删除");
            return map;
        }

        if(bot.getUserId() != user.getId()) {
            map.put("error_message", "你没有权限删除此bot");
            return map;
        }

        botMapper.deleteById(bot_id);  //删除bot
        map.put("error_message", "success");
        return map;
    }
}
