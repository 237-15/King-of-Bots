package com.kob.backend.service.impl.record;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kob.backend.mapper.RecordMapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.Record;
import com.kob.backend.pojo.User;
import com.kob.backend.service.record.GetRecordListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GetRecordListServiceImpl implements GetRecordListService {
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public JSONObject getRecordList(Integer page) {
        IPage<Record> recordIPage = new Page<>(page, 8);  //Mybatis的一个APL，page表示第几页，10表示每一页有10条信息
        QueryWrapper<Record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");  //表示查找关于id的倒序排列  orderByDesc是倒序
        List<Record> records = recordMapper.selectPage(recordIPage, queryWrapper).getRecords();  //固定写法

        JSONObject resp = new JSONObject();  //传给前端用的
        List<JSONObject> items = new LinkedList<>();  //这一页10条信息的详细信息

        for(Record record : records) {  //遍历刚刚查询的每场对局的信息
            User userA = userMapper.selectById(record.getAId());  //取出玩家A的信息
            User userB = userMapper.selectById(record.getBId());  //取出玩家B的信息

            String usernameA = userA.getUsername();  //取出玩家A和B的用户名和头像
            String usernameB = userB.getUsername();
            String photoA = userA.getPhoto();
            String photoB = userB.getPhoto();

            String result = "平局 ";  //判断赢的玩家
            if("A".equals(record.getLoser())) result = "B赢";
            else if("B".equals(record.getLoser())) result = "A赢";

            JSONObject item = new JSONObject();
            item.put("usernameA", usernameA);
            item.put("usernameB", usernameB);
            item.put("photoA", photoA);
            item.put("photoB", photoB);
            item.put("result", result);
            item.put("record", record);

            items.add(item);
        }

        resp.put("records", items);
        resp.put("record_count", recordMapper.selectCount(null));  //recordMapper.selectCount(null)表示有多少条信息，null参数表示查询条件

        return resp;
    }
}
