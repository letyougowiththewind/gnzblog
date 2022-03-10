package com.gnz.blog.controller;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gnz.blog.model.dto.ArticleDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.gnz.blog.model.vo.*;
import com.gnz.blog.service.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Api("与文章相关的api接口")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @ApiOperation("批量获取文章")
    @GetMapping("/articles")
    public Results<PageVO> getArticles(
            @ApiParam("页码")
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        return Results.ok(articleService.getArticles(page, limit));
    }

    @PostMapping("/auth/articles")
    @ApiOperation("新增文章")
    public Results postArticles(@ApiParam(name = "文章信息", value = "传入json格式", required = true)
                                @RequestBody @Valid ArticleDTO articleDTO) {
        String id = articleService.insArticle(articleDTO);
        return Results.ok(MapUtil.of("id", id));
    }

    @ApiOperation("获取个人豆瓣影视数据")
    @GetMapping("/douban")
    public Results<DoubanVO> getDouban() throws IOException {
        return Results.ok(articleService.getDouban());
    }

    @GetMapping("/article/{id}")
    @ApiOperation("根据id查询文章信息")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String", paramType = "path")
    public Results<ArticleVO> getArticle(@PathVariable String id) {
        ArticleVO articleVO = articleService.findById(id);
        return Results.ok(articleVO);
    }

    @DeleteMapping("/auth/article/{id}")
    @ApiOperation("根据id删除文章")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String", paramType = "path")
    public Results deleteArticle(@PathVariable String id) {
        articleService.deleteArticle(id);
        return Results.ok("删除成功", null);
    }

    @PutMapping("/auth/article/{id}")
    @ApiOperation("修改文章")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "String", paramType = "path")
    public Results<Map<String, Object>> putArticles(@ApiParam(name = "要修改的文章信息", value = "传入json格式", required = true)
                                                    @RequestBody ArticleDTO articleDTO,
                                                    @PathVariable String id) {
        articleService.updateArticle(articleDTO, id);
        return Results.ok("更新成功", MapUtil.of("id", id));
    }

    @GetMapping("/timeline")
    @ApiOperation("获取文章时间线")
    public Results<List<TimelineVO>> getTimeline() {
        List<TimelineVO> timeline = articleService.timeline();
        return Results.ok(timeline);
    }


}
