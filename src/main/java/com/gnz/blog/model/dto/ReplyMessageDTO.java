package com.gnz.blog.model.dto;

import io.swagger.annotations.*;
import lombok.*;

@Data
@ApiModel(value = "回复信息", description = "留言回复信息的封装")
public class ReplyMessageDTO {
    @ApiModelProperty(notes = "留言状态", example = "1")
    private Integer status;
    @ApiModelProperty(notes = "回复内容", example = "看了你的留言，......")
    private String replyContent;
}
