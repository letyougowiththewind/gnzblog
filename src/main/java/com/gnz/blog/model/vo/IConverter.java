package com.gnz.blog.model.vo;

public interface IConverter<T,E> {
    /**
     * VO 转换函数
     *
     * @param t 目标对象
     * @return 转换结果
     */
    E convertToVO(T t);
}
