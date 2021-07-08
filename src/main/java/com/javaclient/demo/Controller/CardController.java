package com.javaclient.demo.Controller;

import com.javaclient.demo.dao.VideoDao;
import com.javaclient.demo.model.VideoDo;
import com.javaclient.demo.utils.JsonData;
import com.javaclient.demo.utils.JsonUtil;
import com.javaclient.demo.vo.CardItemVO;
import com.javaclient.demo.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Blue_Sky 7/8/21
 */

@RestController
@RequestMapping("api/v1/card")
public class CardController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private VideoDao videoDao;

    @RequestMapping("add")
    public JsonData addToCard(int videoId, int buyNum){

        // the return data container for data got back from redis
        String result = "";

        BoundHashOperations<String, Object, Object> myCard = getMyCardOps();
        Object cacheObject = myCard.get(videoId + "");

        if(cacheObject != null){
            result = (String) cacheObject;
        }

        // if there is no product in the card
        if(cacheObject == null){
            CardItemVO cardItemVO = new CardItemVO();
            VideoDo videoDo = videoDao.findDetailById(videoId);

            cardItemVO.setBuyNum(buyNum);
            cardItemVO.setPrice(videoDo.getPrice());
            cardItemVO.setProductTitle(videoDo.getTitle());
            cardItemVO.setProductId(videoDo.getId());
            cardItemVO.setProductImg(videoDo.getImg());

            myCard.put(videoId+"", JsonUtil.objectToJson(cardItemVO));
        }else {
            // if does have video in the card
            // the add++
            // we can also ahh this
            CardItemVO cardItemVO = JsonUtil.jsonToPojo(result, CardItemVO.class);
            cardItemVO.setBuyNum(cardItemVO.getBuyNum()+buyNum);
            myCard.put(videoId+"",JsonUtil.objectToJson(cardItemVO));
        }
        return JsonData.buildSuccess();

    }

    @RequestMapping("cardInfo")
    public JsonData getMyCardInfo(){
        BoundHashOperations<String, Object, Object> myCard = getMyCardOps();
        List<Object> itemList = myCard.values();

        // list to store all the card items got from redis json to pojo
        List<CardItemVO> cardItemVOList = new ArrayList<>();

        for (Object item : itemList){
            CardItemVO cardItemVO = JsonUtil.jsonToPojo((String) item, CardItemVO.class);
            cardItemVOList.add(cardItemVO);
        }
        CartVO cartVO =new CartVO();
        cartVO.setCartItems(cardItemVOList);

        return JsonData.buildSuccess(cartVO);
    }

    /**
     * emptying user card
     * @return
     */
    @RequestMapping("flush")
    public JsonData clearCard(){
        String key = getCardKey();
        redisTemplate.delete(key);
        return JsonData.buildSuccess();
    }

    private BoundHashOperations<String,Object,Object> getMyCardOps(){
        String key = getCardKey();
        return redisTemplate.boundHashOps(key);
    }

    /**
     *
     * @return cardKey is the key to find data from our catch storage redis
     */
    private String getCardKey(){
        // in reality you need to get this from user or JWT token
        int userId = 20;
        String cardKey = String.format("video:card:key:%s",userId);
        return cardKey;

    }
}
