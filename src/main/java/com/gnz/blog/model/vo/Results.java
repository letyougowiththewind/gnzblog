package com.gnz.blog.model.vo;

import io.swagger.annotations.*;
import lombok.*;
import lombok.experimental.*;
import com.gnz.blog.model.enums.*;
import com.gnz.blog.utils.*;

@Data
@Accessors(chain = true)
@Builder
@ApiModel("通用接口返回对象")
public class Results<T> {
    @ApiModelProperty(required = true, notes = "结果码", example = "0")
    private int code;
    @ApiModelProperty(required = true, notes = "返回信息", example = "操作成功")
    private String msg;
    @ApiModelProperty(required = true, notes = "返回数据", example = "{\"id\":2001}")
    private T data;
    @ApiModelProperty(required = true, notes = "时间戳", example = "2020-06-29 09:07:34")
    private String timestamp;

    /**
     * 操作成功之后的返回结果
     *
     * @param data 需要返回的数据
     * @return 返回数据的封装
     */
    public static <T> Results<T> ok(T data) {
        return Results.<T>builder()
                .msg("操作成功")
                .data(data)
                .timestamp(DateTimeUtils.now())
                .build();
    }


    /**
     * 根据 IErrorInfo 创建独享
     * @param errorInfo 错误信息
     * @return Results 对象
     */
    public static Results fromErrorInfo(IErrorInfo errorInfo) {
        return Results.builder()
                .code(errorInfo.getCode())
                .msg(errorInfo.getMsg())
                .timestamp(DateTimeUtils.now())
                .build();
    }

    /**
     * 操作成功之后的返回结果
     *
     * @param msg  需要返回的信息
     * @param data 需要返回的数据
     * @return 返回数据的封装
     */
    public static <T> Results<T> ok(String msg, T data) {
        return Results.<T>builder()
                .msg(msg)
                .data(data)
                .timestamp(DateTimeUtils.now())
                .build();
    }

    /**
     * 操作失败之后的返回结果
     *
     * @param data 需要返回的数据
     * @return 返回数据的封装
     */
    public static <T> Results<T> error(T data) {
        return Results.<T>builder()
                .code(5000)
                .msg("操作失败")
                .data(data)
                .timestamp(DateTimeUtils.now())
                .build();
    }

    /**
     * 操作失败之后的返回结果
     *
     * @param msg  需要返回的信息
     * @param data 需要返回的数据
     * @return 返回数据的封装
     */
    public static <T> Results<T> error(String msg, T data) {
        return Results.<T>builder()
                .code(5000)
                .msg(msg)
                .data(data)
                .timestamp(DateTimeUtils.now())
                .build();
    }
}
