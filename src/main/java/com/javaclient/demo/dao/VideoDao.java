package com.javaclient.demo.dao;

import com.javaclient.demo.model.VideoDo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Blue_Sky 7/8/21
 */
@Repository
public class VideoDao {

    private static Map<Integer, VideoDo> map = new HashMap<>();

    static {
        map.put(1,new VideoDo(1,"Industrial PaaS cloud platform + SpringCloudAlibaba integrated project actual combat (finished)","topdent.net",1099));
        map.put(2,new VideoDo(2,"Play with the new version of high-performance RabbitMQ containerized distributed cluster combat","topdent.net",79));
        map.put(3,new VideoDo(3,"The new version of the back-end effect enhancement artifact MybatisPlus+SwaggerUI3.X+Lombok","topdent.net",49));
        map.put(4,new VideoDo(4,"Play Nginx distributed architecture practical tutorial from zero basic to advanced","topdent.net",49));
        map.put(5,new VideoDo(5,"ssm new version SpringBoot2.3/spring5/mybatis3","topdent.net",49));
        map.put(6,new VideoDo(6,"The new generation of microservice family bucket AlibabaCloud+SpringCloud actual combat","topdent.net",59));
    }


    /**
     * Simulate to find from the database
     * @param videoId
     * @return
     */
    public VideoDo findDetailById(int videoId) {
        return map.get(videoId);
    }
}
