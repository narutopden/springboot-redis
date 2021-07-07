package com.javaclient.demo;

import com.javaclient.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate = new RedisTemplate();

    @Autowired
    private StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();

    @Test
    void contextLoads() {
//        ValueOperations valueOperations = redisTemplate.opsForValue();
        redisTemplate.opsForValue().set("names","topden");
//        stringRedisTemplate.opsForValue().set("lastname","tsering");
    }

    @Test
    void redContext(){
       String str = (String) redisTemplate.opsForValue().get("names");
       System.out.println(str);
       String str1 = stringRedisTemplate.opsForValue().get("names");
       System.out.println(str1);
    }

    @Test
    public void seriliza(){
        User user = new User();
        user.setId(12);
        user.setName("topde");
        user.setPassword("tttooopp");
        redisTemplate.opsForValue().set("id-1",user);
    }

}
