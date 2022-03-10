package com.gnz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.gnz.blog.dao.*;
import com.gnz.blog.model.entity.*;
import com.gnz.blog.model.vo.*;

import java.util.*;
import java.util.stream.*;

@Service
@Slf4j
public class TagService {
    @Autowired
    private ArticleMapper articleMapper;

    /***
     * 查询所有标签
     * @return 标签集合
     */
    public Set<String> getAllTags() {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("tags");
        List<ArticlePO> articles = articleMapper.selectList(wrapper);
        Set<String> tags = articles.stream()
                .map(ArticlePO::getTags)
                .flatMap(s -> Arrays.stream(s.split(",")))
                .collect(Collectors.toSet());
        log.info("tags : {}", tags);
        return tags.isEmpty() ? new HashSet<>() : tags;
    }

    public PageVO<ArticleVO> getArticleByTag(String tagName, Integer page, Integer limit) {
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select(ArticlePO.class, i -> !"content".equals(i.getColumn()))
                .like("tags", tagName);
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
