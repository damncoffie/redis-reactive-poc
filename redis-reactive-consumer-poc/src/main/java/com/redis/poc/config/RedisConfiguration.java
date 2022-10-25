package com.redis.poc.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfiguration {

    @Value("${redis.url}")
    private String url;

    @Bean
    public Config redisConfig(JsonJacksonCodec codec) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(url);
        config.setCodec(codec);
        return config;
    }

    @Bean
    public RedissonReactiveClient redissonReactiveClient(Config config) {
        return Redisson.create(config).reactive();
    }

    @Bean // just for cacheable annotation to work
    public CacheManager cacheManager(JsonJacksonCodec codec, Config redisConfig) {
        Map<String, CacheConfig> config = new HashMap<>();
        // create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
        // config.put("testMap", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));

        return new RedissonSpringCacheManager(Redisson.create(redisConfig), config, codec);
    }

    @Bean
    public JsonJacksonCodec jsonJacksonCodec() {
        return new JsonJacksonCodec();
    }
}
