package com.kob.backend.mapper;

//mapper层（也叫Dao层）：将pojo层的class中的操作，映射成sql语句

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
