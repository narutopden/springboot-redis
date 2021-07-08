package com.javaclient.demo.Controller;

import com.javaclient.demo.model.VideoDo;
import com.javaclient.demo.utils.JsonData;
import com.javaclient.demo.vo.UserPointVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author Blue_Sky 7/7/21
 */

@RestController
@RequestMapping("/api/v1/rank")
public class RankController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String DAILY_RANK_KEY = "video:rank:daily";

    /**
     * video rank list on the basis of view or liked
     * @return
     */
    @RequestMapping("daily_rank")
    public JsonData videoDailyRank(){
        List<VideoDo> range = redisTemplate.opsForList().range(DAILY_RANK_KEY, 0, -1);
        return JsonData.buildSuccess(range);
    }

    /**
     * returning the top to bottom rank basis on score
     * @return
     */
    @RequestMapping("player_rank")
    public JsonData playerRankAsc(){
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        Set<UserPointVO> userPointVOS = operations.reverseRange(0, -1);
        return JsonData.buildSuccess(userPointVOS);
    }

    /**
     * returning the top 3 rank
     * @return
     */
    @RequestMapping("top_three")
    public JsonData TopRanks(){
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        Set<UserPointVO> userPointVOS = operations.reverseRange(0, 3);
        return JsonData.buildSuccess(userPointVOS);
    }


    /**
     *     returning the person base on the username and phone number
     * @param username
     * @param phone
     * @return
     */
    @RequestMapping("find_rank")
    public JsonData findRank(String username, String phone){
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        UserPointVO userPointVO = new UserPointVO(username, phone);
        Long rank = operations.reverseRank(userPointVO);
        System.out.println(++rank);
    return JsonData.buildSuccess(rank);
    }

    /**
     * returning the new list up getting increment point
     * @param username
     * @param phone
     * @param point
     * @return
     */
    @RequestMapping("upRank")
    public JsonData upRank(String username, String phone,int point){
        BoundZSetOperations<String, UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        UserPointVO userPointVO = new UserPointVO(username, phone);
        operations.incrementScore(userPointVO,point);
        Set<UserPointVO> userPointVOS = operations.reverseRange(0, -1);
        return JsonData.buildSuccess(userPointVOS);
    }

    @RequestMapping("showScore")
    public JsonData showScore(String username, String phone){
        BoundZSetOperations<String,UserPointVO> operations = redisTemplate.boundZSetOps("point:rank:real");
        UserPointVO userPointVO = new UserPointVO(username, phone);
        Double score = operations.score(userPointVO);
        return JsonData.buildSuccess(score);

    }
}
