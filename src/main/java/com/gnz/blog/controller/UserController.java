package com.gnz.blog.controller;

import cn.hutool.core.map.MapUtil;
import com.gnz.blog.model.vo.Results;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.gnz.blog.constants.*;
import com.gnz.blog.model.comm.*;
import com.gnz.blog.model.dto.*;
import com.gnz.blog.service.*;
import javax.servlet.http.*;
import javax.validation.*;
import java.util.Map;

@Api("与用户相关的api接口")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Results<Map<String, Object>> login(@ApiParam(name = "用户登录信息", value = "传入json格式", required = true)
                                              @RequestBody @Valid UserDTO userDTO) {
        String token = userService.checkUsernamePassword(userDTO);
        return Results.ok("登录成功", MapUtil.of("token", token));
    }
    @PostMapping("/auth/logout")
    @ApiOperation("用户注销登录")
    public Results<Object> logout(@RequestAttribute("token") String token) {
        redisTemplate.opsForSet().add(JwtConstants.REDIS_KEY, token);
        return Results.ok("退出登录成功", null);
    }

}
