package com.gnz.blog.interceptor;


import cn.hutool.core.util.*;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.*;
import org.springframework.web.servlet.*;
import com.gnz.blog.constants.*;
import com.gnz.blog.exception.*;
import com.gnz.blog.model.enums.*;
import com.gnz.blog.utils.*;

import javax.servlet.http.*;
import java.util.*;


@Slf4j
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String token = req.getHeader(JwtConstants.TOKEN_HEADER);
        // 判断 token 是否非空，判断 token 的前缀
        if (Objects.isNull(token) || !token.startsWith(JwtConstants.TOKEN_PREFIX)) {
            throw new BlogException(ErrorInfoEnum.NOT_LOGIN);
        }
        // 判断token是否过期
        token = token.replace(JwtConstants.TOKEN_PREFIX, StrUtil.EMPTY);
        if (JwtUtils.isTokenExpired(token)) {
            throw new BlogException(ErrorInfoEnum.TOKEN_EXPIRED);
        }
        // 判断 token 是否失效
        Boolean isMember = redisTemplate.opsForSet().isMember(JwtConstants.REDIS_KEY, token);
        if (isMember) {
            throw new BlogException(ErrorInfoEnum.TOKEN_INVALID);
        }
        Claims claims = JwtUtils.getTokenBody(token);
        String[] roles = Optional.ofNullable(claims.get(JwtConstants.ROLE_CLAIMS))
                .map(r -> r.toString().split(","))
                .orElse(new String[0]);
        // 判断角色是否正确
        if (!ArrayUtil.contains(roles, UserRoleEnum.ADMIN.getValue())) {
            throw new BlogException(ErrorInfoEnum.NO_AUTHORITY);
        }
        req.setAttribute("token", token);
        return true;
    }
}

