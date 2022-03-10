package com.gnz.blog.model.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.gnz.blog.model.entity.CommentPO;
import com.gnz.blog.model.entity.MessagePO;
import com.gnz.blog.model.enums.MessageStatusEnum;
import com.gnz.blog.utils.DateTimeUtils;
import lombok.Data;

@Data
public class CommentVO {
    private Long id;
    private String nickname;
    private String content;
    private String email;
    private String gmtCreate;
    private String status;
    private String replyContent;
    private String gmtReply;
    private String articleId;

    public static CommentVO fromCommentPO(CommentPO commentPO) {
        return new Converter().convertToVO(commentPO);
    }

    /**
     * 将PO转换成VO的方法
     */
    private static class Converter implements IConverter<CommentPO, CommentVO> {
        @Override
        public CommentVO convertToVO(CommentPO commentPO) {
            CommentVO vo = new CommentVO();
            BeanUtil.copyProperties(commentPO, vo, CopyOptions.create().ignoreError());
            vo.setGmtCreate(DateTimeUtils.FormatDatetime(commentPO.getGmtCreate()));
            vo.setGmtReply(DateTimeUtils.FormatDatetime(commentPO.getGmtReply()));
            //复用了message的enum
            for (MessageStatusEnum e : MessageStatusEnum.values()) {
                if (e.getFlag() == commentPO.getStatus()) {
                    vo.setStatus(e.getNotes());
                }
            }
            return vo;
        }
    }
}
