package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author sun
 * @create 2022-04-18 12:09
 */

@Configuration
public class RedisConfig {


    /**
     *
     * @param factory 参数的值会自动注入到bean中
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        //序列化的形式设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());


        //设置普通的value序列化方式
        template.setValueSerializer(RedisSerializer.json());

        //设置hash key 的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());

        //设置hash  value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        //触发生效
        template.afterPropertiesSet();
        return template;
    }
}
