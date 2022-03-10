package com.gnz.blog.exception;

import com.gnz.blog.model.enums.*;
import com.gnz.blog.model.vo.*;
import com.gnz.blog.utils.*;

/**
 * 自定义异常BlogException来表示博客系统的异常
 */
public class BlogException extends RuntimeException {
    private final IErrorInfo errorInfo;

    public BlogException(IErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * 将异常转换为 ResultVO 对象返回给前端
     *
     * @return 封装了异常信息的 ResultVO 对象
     */
    public Results toResultVO() {
        return Results.fromErrorInfo(errorInfo);
    }
}
