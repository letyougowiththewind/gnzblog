package com.gnz.blog.model.vo;

import cn.hutool.core.bean.*;
import cn.hutool.core.bean.copier.*;
import lombok.*;
import com.gnz.blog.model.entity.*;
import com.gnz.blog.model.enums.*;
import com.gnz.blog.utils.*;

@Data
public class MessageVO {
    private Long id;
    private String nickname;
    private String content;
    private String email;
    private String gmtCreate;
    private String status;
    private String replyContent;
    private String gmtReply;

    public static MessageVO fromMessagePO(MessagePO messagePO) {
        return new Converter().convertToVO(messagePO);
    }

    private static class Converter implements IConverter<MessagePO, MessageVO> {
        @Override
        public MessageVO convertToVO(MessagePO messagePO) {
            MessageVO vo = new MessageVO();
            BeanUtil.copyProperties(messagePO, vo, CopyOptions.create().ignoreError());
            vo.setGmtCreate(DateTimeUtils.FormatDatetime(messagePO.getGmtCreate()));
            vo.setGmtReply(DateTimeUtils.FormatDatetime(messagePO.getGmtReply()));
            for (MessageStatusEnum e : MessageStatusEnum.values()) {
                if (e.getFlag() == messagePO.getStatus()) {
                    vo.setStatus(e.getNotes());
                }
            }
            return vo;
        }
    }
}
