package com.gnz.blog.model.dto;

import cn.hutool.core.bean.*;
import io.swagger.annotations.*;
import lombok.*;
import com.gnz.blog.model.entity.*;
import com.gnz.blog.model.enums.*;

import javax.validation.constraints.*;

@Data
@ApiModel(value = "留言类", description = "前端传入的留言信息")
public class MessageDTO {
    @NotEmpty(message = "留言昵称不能为空")
    @ApiModelProperty(notes = "留言昵称", example = "竹林笔墨")
    private String nickname;
    @NotEmpty(message = "留言内容不能为空")
    @ApiModelProperty(notes = "留言内容", example = "加油鸭 ~ ")
    private String content;
    @NotEmpty(message = "留言者邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(notes = "留言者邮箱", example = "1413979079@qq.com")
    private String email;

    public MessagePO toMessagePO() {
        return new Converter().convertToPO(this);
    }

    private static class Converter implements IConverter<MessageDTO, MessagePO> {
        @Override
        public MessagePO convertToPO(MessageDTO messageDTO) {
            MessagePO po = new MessagePO();
            BeanUtil.copyProperties(messageDTO, po);
            po.setGmtCreate(System.currentTimeMillis());
            po.setStatus(MessageStatusEnum.UNREAD.getFlag());
            return po;
        }
    }
}
