package com.gnz.blog.service;

import cn.hutool.core.convert.*;
import cn.hutool.setting.*;
import com.baomidou.mybatisplus.core.conditions.query.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.gnz.blog.dao.*;
import com.gnz.blog.model.comm.*;
import com.gnz.blog.model.entity.*;
import com.gnz.blog.model.vo.*;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CommService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Setting setting;

    public BlogInfoVO getBlogInfo() {
        BlogSetting blogSetting = BlogSetting.fromSetting(setting);
        BlogInfoVO vo = BlogInfoVO.fromBlogSetting(blogSetting);
        //放入总浏览数
        QueryWrapper<ArticlePO> wrapper = new QueryWrapper<>();
        wrapper.select("sum(views) as total_views");
        List<Map<String, Object>> maps = articleMapper.selectMaps(wrapper);
        int totalViews = 0;
        if (!maps.isEmpty()) {
            totalViews = Convert.toInt(maps.get(0).get("total_views"), 0);
        }
        Integer articleCount = articleMapper.selectCount(null);

        //放入总评论数
        QueryWrapper<CommentPO> wrapper2 = new QueryWrapper<>();
        wrapper2.select("count(nickname) as total_comments");
        List<Map<String, Object>> commentsMaps = commentMapper.selectMaps(wrapper2);
        int totalComments = 0;
        if (!commentsMaps.isEmpty()) {
            totalComments = Convert.toInt(commentsMaps.get(0).get("total_comments"), 0);
        }
        vo.setTotalViews(totalViews);
        vo.setArticleCount(articleCount);
        vo.setTotalComments(totalComments);
        return vo;
    }
}
