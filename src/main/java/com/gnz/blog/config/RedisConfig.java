package com.gnz.blog.config;

import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.data.redis.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.lettuce.*;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.*;

import java.io.*;
import java.nio.charset.*;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        // key的序列化器设置成StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        // 解决中文乱码问题
        template.setValueSerializer(new GenericToStringSerializer<>(String.class, StandardCharsets.UTF_8));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
