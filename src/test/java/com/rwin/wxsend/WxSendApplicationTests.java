package com.rwin.wxsend;

import com.rwin.wxsend.dao.mapper.ConfigMapper;
import com.rwin.wxsend.entity.dto.ModelFiled;
import com.rwin.wxsend.filed.FiledDataUtil;
import com.rwin.wxsend.task.SendMsgTask;
import com.rwin.wxsend.way.WayFilterUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Log4j2
class WxSendApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private SendMsgTask sendMsgTask;

    @Test
    void contextLoads() {

    }


    @Test
    void getWeather(){
        ModelFiled modelFiled = new ModelFiled();
        modelFiled.setKey("weather.temp");
        modelFiled.setValue("浙江|杭州");
        log.error("天气最高温：" + FiledDataUtil.getByFiled(modelFiled));
    }

    @Test
    void sendMsg(){
        sendMsgTask.run();
    }


    @Test
    void testredis(){
        redisTemplate.opsForValue().set("123", 1,5000, TimeUnit.SECONDS);
    }


    @Test
    void testWay(){
        boolean a = WayFilterUtil.isAllow("workday", "", "15:45", new Date());
        System.out.println(a);

    }


}
