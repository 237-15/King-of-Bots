package com.kob.backend.pojo;
//pojo层：将数据库中的表对应成Java中的Class

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @TableId(type = IdType.AUTO)  //ID自增1
    private Integer id;
    private String username;
    private String password;
    private String photo;
    private Integer rating;
}

