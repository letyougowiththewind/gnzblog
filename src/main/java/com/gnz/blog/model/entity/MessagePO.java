package com.gnz.blog.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.*;

@Data
@TableName("messages")
public class MessagePO implements Serializable {
    private static final long serialVersionUID = -923353340624107729L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String nickname;
    private String content;
    private String email;
    private Long gmtCreate;
    private Integer status;
    private String replyContent;
    private Long gmtReply;
}
