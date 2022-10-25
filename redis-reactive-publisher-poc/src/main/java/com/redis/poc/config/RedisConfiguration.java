package com.redis.poc.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {

    @Bean
    public Config redisConfig(JsonJacksonCodec codec) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        config.setCodec(codec);
        return config;
    }

    @Bean
    public RedissonReactiveClient redissonReactiveClient(Config config) {
        return Redisson.create(config).reactive();
    }

    @Bean
    public JsonJacksonCodec jsonJacksonCodec() {
        return new JsonJacksonCodec();
    }
}
