package com.gnz.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.gnz.blog.dao.*;
import com.gnz.blog.exception.*;
import com.gnz.blog.model.dto.*;
import com.gnz.blog.model.entity.*;
import com.gnz.blog.model.vo.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

import static com.gnz.blog.model.enums.ErrorInfoEnum.*;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private CommentMapper commentMapper;

    public PageVO<MessageVO> getMessages(Integer page, Integer limit) {
        Page<MessagePO> poPage = messageMapper.selectPage(new Page<>(page, limit), null);
        List<MessageVO> messageVOS = poPage.getRecords().stream()
                .map(MessageVO::fromMessagePO)
                .collect(Collectors.toList());
        PageVO<MessageVO> pageVO = PageVO.<MessageVO>builder()
                .records(messageVOS.isEmpty() ? new ArrayList<>() : messageVOS)
                .total(poPage.getTotal())
                .current(poPage.getCurrent())
                .size(poPage.getSize())
                .build();
        return pageVO;
    }

    /**
     * 在留言模块下加入获取评论分页对象的方法
     * @param page
     * @param limit
     * @return
     */
    public PageVO<CommentVO> getComment(Integer page, Integer limit,String id) throws NoSuchFieldException, IllegalAccessException {
        Page<CommentPO> poPage = commentMapper.selectPage(new Page<>(page, limit), null);
        List<CommentVO> commentVOS = poPage.getRecords().stream()
                .map(CommentVO::fromCommentPO)
                .filter(commentVO  -> id.equals(commentVO.getArticleId()))
                .collect(Collectors.toList());

        PageVO<CommentVO> pageVO = PageVO.<CommentVO>builder()
                .records(commentVOS.isEmpty() ? new ArrayList<>() : commentVOS)
                .total(poPage.getTotal())
                .current(poPage.getCurrent())
                .size(poPage.getSize())
                .build();
        return pageVO;
    }

    public void insMessage(MessageDTO messageDTO) {
        MessagePO messagePO = messageDTO.toMessagePO();
        messageMapper.insert(messagePO);
    }

    /**
     * 在留言模块下加入新增评论的方法
     * @param commentDTO
     */
    public void insComment(CommentDTO commentDTO,String id){
        CommentPO commentPO = commentDTO.toMessagePO();
        commentMapper.insert(commentPO);
    }

    public MessageVO findById(Long id) {
        MessagePO messagePO = messageMapper.selectById(id);
        MessageVO vo = MessageVO.fromMessagePO(messagePO);
        return vo;
    }

    /**
     * 在留言模块下加入根据评论id查找评论的方法
     * @param id
     * @return
     */
    public CommentVO findByCommentId(Long id) {
        CommentPO commentPO = commentMapper.selectById(id);
        CommentVO vo = CommentVO.fromCommentPO(commentPO);
        return vo;
    }

    public void deleteMessage(Long id) {
        int i = messageMapper.deleteById(id);
        if (i <= 0) {
            throw new BlogException(INVALID_ID);
        }
    }

    public void updateById(Long id, ReplyMessageDTO replyMessageDTO) {
        MessagePO messagePO = messageMapper.selectById(id);
        if (Objects.isNull(messagePO)) {
            throw new BlogException(INVALID_ID);
        }
        MessagePO messageForUpdate = new MessagePO();
        messageForUpdate.setId(messagePO.getId());
        Integer status = replyMessageDTO.getStatus();
        if (Objects.nonNull(status)) {
            messageForUpdate.setStatus(status);
        }
        String content = replyMessageDTO.getReplyContent();
        if (Objects.nonNull(content)) {
            messageForUpdate.setReplyContent(content);
            messageForUpdate.setGmtReply(System.currentTimeMillis());
        }
        messageMapper.updateById(messageForUpdate);
    }
}
