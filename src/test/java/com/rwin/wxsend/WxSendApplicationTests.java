package com.rwin.wxsend;

import cn.hutool.http.HttpUtil;
import com.rwin.wxsend.dao.mapper.ConfigMapper;
import com.rwin.wxsend.entity.ModelConfig;
import com.rwin.wxsend.entity.dto.ModelFiled;
import com.rwin.wxsend.task.SendMsgTask;
import com.rwin.wxsend.util.FiledDataUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class WxSendApplicationTests {


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

}
