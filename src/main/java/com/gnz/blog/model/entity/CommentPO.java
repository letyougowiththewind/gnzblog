package com.gnz.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("comment")
public class CommentPO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String nickname;
    private String content;
    private String email;
    private Long gmtCreate;
    private Integer status;
    private String replyContent;
    private Long gmtReply;
    private String articleId;
}
