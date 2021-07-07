package com.javaclient.demo.Controller;

import com.google.code.kaptcha.Producer;
import com.javaclient.demo.utils.CommonUtils;
import com.javaclient.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/captcha")
public class CaptchaController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private Producer captchaProducer;

    @GetMapping("get_captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){

        String captchaText = captchaProducer.createText();
        String key = getCaptchaKey(request);
        stringRedisTemplate.opsForValue().set(key,captchaText,10, TimeUnit.MINUTES);
        BufferedImage image = captchaProducer.createImage(captchaText);
        ServletOutputStream outputStream = null;
        try{
            outputStream = response.getOutputStream();
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @GetMapping("send_captcha")
    public JsonData verifyCaptcha(@RequestParam(value = "to",required = true) String to,
                                  @RequestParam(value = "captcha", required = true) String captcha,
                                  HttpServletRequest request){
        String key = getCaptchaKey(request);
        String cacheCaptcha = stringRedisTemplate.opsForValue().get(key);

        if(cacheCaptcha != null && captcha != null && captcha.equalsIgnoreCase(cacheCaptcha)){
            stringRedisTemplate.delete(key);

            // TODO service to send OTP

            return JsonData.buildSuccess();
        }else {
            return JsonData.buildError("captcha incorrect");
        }
    }


    public String getCaptchaKey(HttpServletRequest request){

        String ip = CommonUtils.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = "user-service:captcha:"+CommonUtils.MD5(ip+userAgent);
        return key;
    }
}


