package com.gnz.blog.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.gnz.blog.model.comm.*;
import com.gnz.blog.model.vo.*;
import com.gnz.blog.service.*;

@Api("博客统计信息")
@RestController
public class StatisticsController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取文章数统计信息（按月）")
    @GetMapping("/auth/chart/articles")
    public Results<StatisticsVO> getArticles() {
        StatisticsVO statisticsVO = articleService.countArticlesByMonth();
        return Results.ok(statisticsVO);
    }
}
