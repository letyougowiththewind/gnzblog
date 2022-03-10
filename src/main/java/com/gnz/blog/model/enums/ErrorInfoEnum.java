package com.gnz.blog.model.enums;

public enum ErrorInfoEnum implements IErrorInfo{
    SUCCESS(0, "操作成功"),
    MISSING_PARAMETERS(4004, "参数缺失"),
    UNKNOWN_ERROR(5000, "出现未知错误"),
    INVALID_ID(4008, "你的id不合法"),
    RESOURCES_NOT_FOUND(4003, "找不到相应资源"),
    USERNAME_PASSWORD_ERROR(4009, "用户名或密码错误"),
    TOKEN_EXPIRED(4001, "您的token已过期"),
    NOT_LOGIN(4002, "请先登录再进行访问"),
    NO_AUTHORITY(4005, "对不起，您没有权限访问该接口"),
    TOKEN_INVALID(4004, "无效的token");


    private int code;
    private String msg;

    @Override
    public String getMsg() {
        return msg;
    }

    ErrorInfoEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }
}
