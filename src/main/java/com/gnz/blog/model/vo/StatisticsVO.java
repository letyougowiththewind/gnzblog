package com.gnz.blog.model.vo;

import lombok.*;

import java.util.*;

@Data
public class StatisticsVO {
    private List<String> xAxisData;
    private List<Integer> yAxisData;

    public StatisticsVO() {
        xAxisData = new ArrayList<>();
        yAxisData = new ArrayList<>();
    }

    public void put(String x, Integer y) {
        xAxisData.add(x);
        yAxisData.add(y);
    }
}
