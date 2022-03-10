package com.gnz.blog.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.gnz.blog.model.comm.*;
import com.gnz.blog.model.dto.*;
import com.gnz.blog.model.vo.*;
import com.gnz.blog.service.*;

import javax.validation.*;

@Api("与留言相关的api接口")
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @ApiOperation("分页获取留言")
    @GetMapping("/messages")
    public Results<PageVO<MessageVO>> getMessage(
            @ApiParam("页码")
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        return Results.ok(messageService.getMessages(page, limit));
    }

    @ApiOperation("分页获取评论")
    @GetMapping("/comment")
    public Results<PageVO<CommentVO>> getComment(
            @ApiParam("页码")
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(required = false, defaultValue = "5") Integer limit,String id) throws NoSuchFieldException, IllegalAccessException {
        return Results.ok(messageService.getComment(page, limit,id));
    }

    @PostMapping("/messages")
    @ApiOperation("新增留言")
    public Results<String> postMessage(@ApiParam(name = "留言信息", value = "传入json格式", required = true)
                                       @RequestBody @Valid MessageDTO messageDTO) {
        messageService.insMessage(messageDTO);
        return Results.ok("留言新增成功", null);
    }

    @PostMapping("/comment")
    @ApiOperation("新增评论")
    public Results<String> postComment(@ApiParam(name = "评论信息", value = "传入json格式", required = true)
                                       @RequestBody @Valid CommentDTO commentDTO,String id) {
        messageService.insComment(commentDTO,id);
        return Results.ok("评论新增成功", null);
    }

    @GetMapping("/message/{id}")
    @ApiOperation("根据id查询留言信息")
    @ApiImplicitParam(name = "id", value = "留言id", required = true, dataType = "Long", paramType = "path")
    public Results<MessageVO> getMessage(@PathVariable Long id) {
        MessageVO messageVO = messageService.findById(id);
        return Results.ok(messageVO);
    }

    @GetMapping("/comment/{id}")
    @ApiOperation("根据id查询评论信息")
    @ApiImplicitParam(name = "id", value = "留言id", required = true, dataType = "Long", paramType = "path")
    public Results<CommentVO> getComment(@PathVariable Long id) {
        CommentVO commentVO = messageService.findByCommentId(id);
        return Results.ok(commentVO);
    }

    @DeleteMapping("/auth/message/{id}")
    @ApiOperation("根据id删除留言")
    @ApiImplicitParam(name = "id", value = "留言id", required = true, dataType = "Long", paramType = "path")
    public Results<String> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return Results.ok("删除成功", null);
    }

    @PutMapping("/auth/message/{id}")
    @ApiOperation("修改留言")
    @ApiImplicitParam(name = "id", value = "留言id", required = true, dataType = "Long", paramType = "path")
    public Results<String> putMessage(@ApiParam(name = "要修改的留言信息", value = "传入json格式", required = true)
                                      @RequestBody ReplyMessageDTO replyMessageDTO,
                                      @PathVariable Long id) {
        messageService.updateById(id, replyMessageDTO);
        return Results.ok("更新成功", null);
    }
}
