package com.gnz.blog.dao;

//import com.baomidou.mybatisplus.core.mapper.*;
import com.baomidou.mybatisplus.core.mapper.*;
import com.gnz.blog.model.entity.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticlePO> {
}
