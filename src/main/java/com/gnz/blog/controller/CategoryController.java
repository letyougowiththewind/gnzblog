package com.gnz.blog.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import com.gnz.blog.model.vo.*;
import com.gnz.blog.service.*;
import java.util.*;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("获取所有的文章分类以及对应分类文章数")
    @GetMapping("/categories")
    public Results<List<CategoryVO>> getCategories() {
        return Results.ok(categoryService.getAllCategories());
    }

    @GetMapping("/category/{name}")
    @ApiOperation("根据分类查询文章集合")
    @ApiImplicitParam(name = "name", value = "分类名称", required = true, dataType = "String", paramType = "path")
    public Results<PageVO<ArticleVO>> getArticle(
            @PathVariable("name") String categoryName,
            @ApiParam("页码")
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam("每页存放的记录数")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        PageVO<ArticleVO> pv = categoryService.getArticleByCategory(categoryName, page, limit);
        return Results.ok(pv);
    }
}
