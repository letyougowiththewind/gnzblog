package com.gnz.blog.model.vo;

import lombok.*;

import java.util.*;

@Data
@Builder
public class TimelineVO {
    private String year;
    private List<Item> items;

    @Data
    @Builder
    public static class Item {
        private String id;
        private String gmtCreate;
        private String title;
    }
}
