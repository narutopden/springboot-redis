package com.javaclient.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author Blue_Sky 7/9/21
 */
@Configuration
public class CacheManagerConfig {

    @Bean
    public KeyGenerator springCacheKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return o.getClass().getSimpleName()+"_"
                        +method.getName()+"_"
                        + StringUtils.arrayToDelimitedString(objects,"_");
            }
        };
    }

    @Bean
    public RedisCacheManager cacheManager1Minute(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration config = instanceConfig(60L);
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    /**
     * @Primary this annotation means current function is the default one
     * if none is applied
     * @param connectionFactory
     * @return
     */
    @Bean
    @Primary
    public RedisCacheManager cacheManager1Hour(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration config = instanceConfig(3600L);
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    @Bean
    public RedisCacheManager cacheManager1Day(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration config = instanceConfig(3600 * 24L);
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    private RedisCacheConfiguration instanceConfig(Long ttl) {

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        // Remove the analysis of various @JsonSerialize annotations
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        // Only serialize for non-empty values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Serialize the type into the attribute json string
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(ttl))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

    }

}
