package com.kob.backend.pojo.post.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentLevel2 {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer commentId;
    private Integer belongCommentId;
    private String content;
    private Date createTime;
    private Integer loveCount;
}
