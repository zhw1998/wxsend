package com.rwin.wxsend.init;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import com.rwin.wxsend.entity.dto.DateDto;
import com.rwin.wxsend.util.WayFilterUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目初始化
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/16
 */
@Component
@Log4j2
public class AppInit implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        setDate();
    }



    /**
     * 从date.json获取日期数据
     *
     * @param
     * @return void
     * @author zouhongwei
     * @date 2022/12/16
     */
    public void setDate(){
        try {
            Resource resource = new ClassPathResource("date.json");
            if(resource != null){
                BufferedReader bufferedReader = resource.getReader(CharsetUtil.CHARSET_UTF_8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine())!=null) {
                    sb.append(line);
                }
                List<DateDto> dateDtos = JSON.parseArray(sb.toString(), DateDto.class);
                WayFilterUtil.setDateList(dateDtos);
                Map<String, DateDto> dateDtoMap = new HashMap<>();
                for (DateDto dateDto : dateDtos){
                    dateDtoMap.put(dateDto.getDate(), dateDto);
                }
                WayFilterUtil.setDateDtoMap(dateDtoMap);
            }
        }catch (Exception e){
            log.error("日期数据初始化失败：{}", e);
        }

    }
}
