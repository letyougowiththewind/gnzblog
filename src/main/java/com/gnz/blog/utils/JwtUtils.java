package com.gnz.blog.utils;

import cn.hutool.crypto.*;
import io.jsonwebtoken.*;
import com.gnz.blog.constants.*;

import javax.crypto.*;
import java.util.*;

/**
 * JWT 工具类
 */
public class JwtUtils {
    private static final SecretKey secretKey = SecureUtil.generateKey("HS256", JwtConstants.JWT_SECRET_KEY.getBytes());

    /**
     * 生成 JwtToken
     * @param subject token 中要保存的内容
     * @param roles 角色
     * @return JwtToken
     */
    public static String createToken(String subject, List<String> roles) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + JwtConstants.EXPIRATION * 1000);
        String tokenPrefix = Jwts.builder()
                .setHeaderParam("type", JwtConstants.TOKEN_TYPE)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim(JwtConstants.ROLE_CLAIMS, String.join(",", roles))
                .setIssuer(JwtConstants.TOKEN_ISSUER)
                .setIssuedAt(createdDate)
                .setSubject(subject)
                .setExpiration(expirationDate)
                .compact();
        return JwtConstants.TOKEN_PREFIX + tokenPrefix;
    }

    /**
     * 判断 JwtToken 是否已经过期
     * @param token JwtToken
     * @return 是否过期，true：已经过期
     */
    public static boolean isTokenExpired(String token) {
        Date expiredDate = getTokenBody(token).getExpiration();
        return expiredDate.before(new Date());
    }

    /**
     * 获取 JwtToken 中存储的内容
     * @param token JwtToken
     * @return JwtToken 中存储的内容
     */
    public static String getSubject(String token) {
        return getTokenBody(token).getSubject();
    }

    public static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
