package com.javaclient.demo;

import com.javaclient.demo.model.User;
import com.javaclient.demo.model.VideoDo;
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

    @Test
    public void saveRank(){
        String DAILY_RANK_KEY = "video:rank:daily";
        VideoDo video1 = new VideoDo(3,"PaaS Industrial Microservices Class","blue.net",1099);
        VideoDo video2 = new VideoDo(5,"AlibabaCloud family bucket combat","blue.net",59);
        VideoDo video3 = new VideoDo(53,"SpringBoot2.X+Vue3 comprehensive actual combat","blue.net",49);
        VideoDo video4 = new VideoDo(15,"Play with 23 design patterns + recent actual combat","blue.net",49);
        VideoDo video5 = new VideoDo(45,"Nginx Gateway+LVS+KeepAlive","blue.net",89);

        Long status = redisTemplate.opsForList().leftPushAll(DAILY_RANK_KEY, video5, video4, video3, video2, video1);
        System.out.println("save status: "+status);
    }

    @Test
    public void manualChangeRank(){
        String DAILY_RANK_KEY = "video:rank:daily";
        VideoDo video2 = new VideoDo(2,"Tibetan Lietrature Class ","blue.net",39);

        redisTemplate.opsForList().set(DAILY_RANK_KEY,2,video2);

    }

}
