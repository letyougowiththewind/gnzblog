package com.gnz.blog.utils;

import cn.hutool.core.util.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

public class DateTimeUtils {
    /**
     * 以 Java8 的方式获取当前时间字符串
     *
     * @return 当前时间格式化之后的字符串
     */
    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    /**
     * 以 Java8 的方式将毫秒数转化为字符串，字符串格式：yyyy-MM-dd HH:mm
     *
     * @param millis 毫秒数
     * @return 格式化之后的字符串
     */
    public static String FormatDatetime(Long millis) {
        return formatDatetime(millis, "yyyy-MM-dd HH:mm");
    }
    /**
     * 以 Java8 的方式将毫秒数转化为字符串
     *
     * @param millis  毫秒数
     * @param pattern 格式
     * @return 格式化之后的字符串
     */
    public static String formatDatetime(Long millis, String pattern) {
        if (Objects.isNull(millis)) {
            return StrUtil.EMPTY;
        }
        Instant instant = Instant.ofEpochMilli(millis);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    /**
     * 以 Java8 的方式将毫秒数转化为字符串，字符串格式：yyyy-MM-dd
     *
     * @param millis 毫秒数
     * @return 格式化之后的字符串
     */
    public static String formatDate(Long millis) {
        return formatDatetime(millis, "yyyy-MM-dd");
    }
}
