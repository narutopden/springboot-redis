package com.javaclient.demo;

import com.javaclient.demo.model.User;
import com.javaclient.demo.model.VideoDo;
import com.javaclient.demo.vo.UserPointVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.Set;

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

    @Test
    public void setExample(){
        BoundSetOperations boundSetOps = redisTemplate.boundSetOps("user:tags:1");
        boundSetOps.add("rich","car","dog","iPhone","programmer","java","cat");

        Set<String> set = boundSetOps.members();
        System.out.println(set);

        boundSetOps.remove("cat");
        Set<String> set2 = boundSetOps.members();
        System.out.println(set2);

    }

    @Test
    public void tagOperation(){
        BoundSetOperations personT = redisTemplate.boundSetOps("star:tsering");
        personT.add("A","B","C","D","E");
        System.out.println("total followers from tsering: "+personT.members());

        BoundSetOperations personS = redisTemplate.boundSetOps("star:sonam");
        personS.add("A","B","Y","J","W");
        System.out.println("total followers from sonam: "+personS.members());

        Set uniqueInT = personT.diff("star:sonam");
        System.out.println("unique followers from Tsering"+uniqueInT);

        Set uniqueInS = personS.diff("star:tsering");
        System.out.println("unique followers from Sonam"+uniqueInS);

        Set intersect = personT.intersect("star:sonam");
        System.out.println("common user follower: "+intersect);

        Set union = personT.union("star:sonam");
        System.out.println("common user follower: "+union);

        Boolean isMember = personT.isMember("J");
        System.out.println("is person J follow Tsring? : "+isMember);
    }

    @Test
    public void testData(){

            UserPointVO p1 = new UserPointVO("player main","1332213");
            UserPointVO p2 = new UserPointVO("playerA","13113");
            UserPointVO p3 = new UserPointVO("playerB","242");
            UserPointVO p4 = new UserPointVO("playerC","542345");
            UserPointVO p5 = new UserPointVO("playerD","235");
            UserPointVO p6 = new UserPointVO("playerE","1245");
            UserPointVO p7 = new UserPointVO("playerF","2356432");
            UserPointVO p8 = new UserPointVO("playerG","532332");

            BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");


            operations.add(p1,1);
            operations.add(p2,2);
            operations.add(p3,3);
            operations.add(p4,4);
            operations.add(p5,5);
            operations.add(p6,6);
            operations.add(p7,7);
            operations.add(p8,8);

        }


}
