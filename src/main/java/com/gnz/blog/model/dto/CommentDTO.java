package com.gnz.blog.model.dto;

import cn.hutool.core.bean.BeanUtil;
import com.gnz.blog.model.entity.CommentPO;
import com.gnz.blog.model.entity.MessagePO;
import com.gnz.blog.model.enums.MessageStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "留言类", description = "前端传入的留言信息")
public class CommentDTO {
    @NotEmpty(message = "留言昵称不能为空")
    @ApiModelProperty(notes = "留言昵称", example = "竹林笔墨")
    private String nickname;
    @NotEmpty(message = "留言内容不能为空")
    @ApiModelProperty(notes = "留言内容", example = "加油鸭 ~ ")
    private String content;
    private String articleId;

    public CommentPO toMessagePO() {
        return new Converter().convertToPO(this);
    }

    private static class Converter implements IConverter<CommentDTO, CommentPO> {
        @Override
        public CommentPO convertToPO(CommentDTO commentDTO) {
            CommentPO po = new CommentPO();
            BeanUtil.copyProperties(commentDTO, po);
            po.setGmtCreate(System.currentTimeMillis());
            po.setStatus(MessageStatusEnum.UNREAD.getFlag());

            return po;
        }
    }
}
