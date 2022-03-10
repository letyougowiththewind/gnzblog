package com.gnz.blog.model.dto;

import io.swagger.annotations.*;
import lombok.*;

import javax.validation.constraints.*;

@Data
@ApiModel(value = "用户登录信息类", description = "前端传入的用户登录信息")
public class UserDTO {
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(notes = "用户名", example = "admin")
    private String username;
    @NotEmpty(message = "用户密码不能为空")
    @ApiModelProperty(notes = "用户密码", example = "123")
    private String password;
}
