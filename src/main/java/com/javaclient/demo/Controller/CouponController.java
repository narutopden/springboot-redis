package com.javaclient.demo.Controller;

import com.javaclient.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Blue_Sky 7/7/21
 */
@RequestMapping("/api/v1/coupon")
@RestController
public class CouponController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("get_coupon")
    public JsonData getCoupon(@RequestParam(value = "id", required = true) int couponId){
        String uuid = UUID.randomUUID().toString();
        String lockKey = "lock:coupon:key"+couponId;
        lock(couponId,uuid,lockKey);
        return JsonData.buildSuccess();
    }

    private void lock(int couponId, String uuid, String lockKey){

        // the Lua script for locking
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        // using setIfAbsent method to lock in redis
        Boolean nativeLock = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, Duration.ofSeconds(30));
        System.out.println(uuid+" lock is "+ nativeLock);
        if(nativeLock){ //if the lock is successful
            // TODO some service logic
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                // release the lock
                Long result =  redisTemplate.execute(new DefaultRedisScript<>(script, long.class), Arrays.asList(lockKey), uuid);
                System.out.println("Release lock status: "+result);

            }
        }else {
            // if the previous lock is not released the wait for
            // time and ask again
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // locking after 5 second
            lock(couponId,uuid,lockKey);
        }

    }
}
