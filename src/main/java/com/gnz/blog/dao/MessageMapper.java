package com.gnz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.*;
import org.apache.ibatis.annotations.Mapper;
import com.gnz.blog.model.entity.*;

@Mapper
public interface MessageMapper extends BaseMapper<MessagePO> {
}
