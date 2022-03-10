package com.gnz.blog.controller;

import com.gnz.blog.exception.BlogException;
import com.gnz.blog.model.enums.ErrorInfoEnum;
import com.gnz.blog.service.CommService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gnz.blog.model.vo.*;

@Api("通用接口")
@CrossOrigin
@RestController
public class CommController {

    @Autowired
    private CommService commService;

    @ApiOperation("检查服务端是否正常")
    @GetMapping("/ping")
    public Results ping() {
        throw new BlogException(ErrorInfoEnum.MISSING_PARAMETERS);
    }


    @ApiOperation("获取博客信息")
    @GetMapping("/info")
    public Results<BlogInfoVO> info() {
        BlogInfoVO blogInfo = commService.getBlogInfo();
        return Results.ok(blogInfo);
    }

}

