package com.kob.backend.mapper.post;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.pojo.post.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
