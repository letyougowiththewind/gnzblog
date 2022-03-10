package com.gnz.blog.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DoubanVO<T> {
    protected List<T> records;
}
