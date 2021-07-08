package com.javaclient.demo.Controller;

import com.javaclient.demo.model.VideoDo;
import com.javaclient.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Blue_Sky 7/7/21
 */

@RestController
@RequestMapping("/api/v1/rank")
public class RankController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String DAILY_RANK_KEY = "video:rank:daily";

    @RequestMapping("daily_rank")
    public JsonData videoDailyRank(){
        List<VideoDo> range = redisTemplate.opsForList().range(DAILY_RANK_KEY, 0, -1);
        return JsonData.buildSuccess(range);
    }
}
