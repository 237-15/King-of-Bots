package com.kob.backend.mapper.post.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kob.backend.pojo.post.comment.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
