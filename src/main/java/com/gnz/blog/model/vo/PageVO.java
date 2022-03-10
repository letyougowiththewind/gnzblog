package com.gnz.blog.model.vo;

import lombok.*;

import java.util.*;

@Data
@Builder
public class PageVO<T> {
    protected List<T> records;
    protected long total;
    protected long size;
    protected long current;
}
