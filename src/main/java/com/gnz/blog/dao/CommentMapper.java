package com.gnz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gnz.blog.model.entity.CommentPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<CommentPO> {
}
