package com.example.survey.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
@EnableCaching
public class AppConfig {

    // RedisCacheManager tells Spring to use Redis for all @Cacheable / @CachePut methods
    // TTL = 120 seconds: any key stored in Redis will auto-expire after 2 minutes
    // Uses default JDK serialization — works because Users implements Serializable
    @Bean
    CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(120));

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}
