package com.rwin.wxsend;

import com.rwin.wxsend.dao.mapper.ConfigMapper;
import com.rwin.wxsend.entity.ModelConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WxSendApplicationTests {


    @Autowired
    private ConfigMapper configMapper;

    @Test
    void contextLoads() {

        configMapper.selectList(null);

    }

}
