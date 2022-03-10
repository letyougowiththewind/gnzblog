package com.gnz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.gnz.blog.dao.*;
import com.gnz.blog.model.entity.*;
import com.gnz.blog.model.vo.*;

import java.util.*;
import java.util.stream.*;

@Service
public class CategoryService {
    @Autowired
    private ArticleMapper articleMapper;

    public List<CategoryVO> getAllCategories() {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("category as `name`", "count(1) as `count`").groupBy("category");
        List<CategoryVO> list = articleMapper.selectMaps(wrapper).stream()
                .map(CategoryVO::fromMap)
                .collect(Collectors.toList());
        return list.isEmpty() ? new ArrayList<>() : list;
    }

    public PageVO<ArticleVO> getArticleByCategory(String categoryName, Integer page, Integer limit) {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select(ArticlePO.class, i -> !"content".equals(i.getColumn()))
                .eq("category", categoryName);
        Page<ArticlePO> res = articleMapper.selectPage(new Page<>(page, limit), wrapper);
        List<ArticleVO> articleVOS = res.getRecords().stream()
                .map(ArticleVO::fromArticlePO)
                .collect(Collectors.toList());
        PageVO<ArticleVO> pv = PageVO.<ArticleVO>builder()
                .records(articleVOS.isEmpty() ? new ArrayList<>() : articleVOS)
                .total(res.getTotal())
                .current(res.getCurrent())
                .size(res.getSize())
                .build();
        return pv;
    }
}
