package com.redis.poc.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@Configuration
public class RedissonSpringDataConfig {

    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        final RedisStandaloneConfiguration redisConfig
                = new RedisStandaloneConfiguration("redis://127.0.0.1", 6379);
//        redisConfig.setPassword(SpringBootApp.redisPassword);
//        redisConfig.setDatabase(0);
        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public ReactiveRedisConnection reactiveRedisConnection(final ReactiveRedisConnectionFactory redisConnectionFactory) {
        return redisConnectionFactory.getReactiveConnection();
    }

    @Bean
    public ReactiveRedisOperations<String, Object> redisOperations() {
        final JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
        final RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
                .newSerializationContext(new StringRedisSerializer());
        final RedisSerializationContext<String, Object> context = builder.value(serializer).build();
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory(), context);
    }

}