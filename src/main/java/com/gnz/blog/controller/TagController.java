package com.gnz.blog.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.gnz.blog.model.vo.*;
import com.gnz.blog.service.*;

import java.util.*;

@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @ApiOperation("获取所有的标签")
    @GetMapping("/tags")
    public Results<Set<String>> getTags() {
        return Results.ok(tagService.getAllTags());
    }

    @GetMapping("/tag/{name}")
    @ApiOperation("根据标签查询文章集合")
    @ApiImplicitParam(name = "name", value = "标签名称", required = true, dataType = "String", paramType = "path")
    public Results<PageVO<ArticleVO>> getArticle(
            @PathVariable("name") String tagName,
            @ApiParam("页码")
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        PageVO<ArticleVO> pv = tagService.getArticleByTag(tagName, page, limit);
        return Results.ok(pv);
    }
}
