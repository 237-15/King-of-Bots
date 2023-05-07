package com.kob.backend.pojo.post.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoveCommentLevel2 {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer postId;
    private Integer commentId;
}