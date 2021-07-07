package com.javaclient.demo.Controller;

import com.javaclient.demo.model.VideoCardDo;
import com.javaclient.demo.service.VideoCardService;
import com.javaclient.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequestMapping("/api/v1/video")
@RestController
public class VideoController {

    @Autowired
    VideoCardService videoCardService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * cache key for redis
     */
    private static final String VIDEO_CARD_CACHED_KEY = "video:list:key";



    @GetMapping("list_withCache")
    public JsonData getList(){
        Object obj = redisTemplate.opsForValue().get(VIDEO_CARD_CACHED_KEY);
        if (obj != null){
            List<VideoCardDo> videoCardDos = (List<VideoCardDo>) obj;
            return JsonData.buildSuccess(videoCardDos);
        }else {
            List<VideoCardDo> list = videoCardService.list();
            redisTemplate.opsForValue().set(VIDEO_CARD_CACHED_KEY,list,10, TimeUnit.MINUTES);
            return JsonData.buildSuccess(list);
        }

    }

    @GetMapping("list_nocache")
    public JsonData getList_noCache(){
        List<VideoCardDo> list = videoCardService.list();
        return JsonData.buildSuccess(list);
    }
}
