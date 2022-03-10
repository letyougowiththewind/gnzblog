package com.gnz.blog.model.vo;

import lombok.*;

import java.util.*;

@Data
@Builder
public class CategoryVO {
    private String name;
    private String count;

    public static CategoryVO fromMap(Map<String, Object> map) {
        return new CategoryVO.Converter().convertToVO(map);
    }

    private static class Converter implements IConverter<Map<String,Object>, CategoryVO> {
        @Override
        public CategoryVO convertToVO(Map<String, Object> map) {
            CategoryVO vo = CategoryVO.builder()
                    .name(String.valueOf(map.get("name")))
                    .count(String.valueOf(map.get("count")))
                    .build();
            return vo;
        }
    }
}
