package com.rwin.wxsend.filed;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.rwin.wxsend.entity.dto.DateDto;
import com.rwin.wxsend.filed.data.Weather;
import com.rwin.wxsend.util.DateUtil;
import com.rwin.wxsend.util.WayFilterUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 在这新增实现方法
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/19
 */
@Log4j2
@Component
public class FiledMethod {

    @Autowired
    private Environment environment;



    /**
     * 已实现的方法字段
     */
    public static Map<String, String> filedMap = new HashMap<>();


    /**
     * 获取星期
     * value
     * @return java.lang.String
     * @date 2022/12/19
     */
    @Filed(name = "当天星期[无]", value = "curWeekDay")
    public String getCurWeekDay(String value){
        String curDate = DateUtil.getCurrentStringDate();
        DateDto dateDto = WayFilterUtil.getDateDtoMap().get(curDate);
        return dateDto.getWeek();
    }

    /**
     * 已过天数 yyyy-MM-dd
     * @param value
     * @return
     */
    @Filed(name = "已过天数[yyyy-MM-dd]", value = "passDays")
    public String getPassDays(String value){
        Date importantDay = DateUtil.convertString2Date(value);
        return DateUtil.diffDay(new Date(), importantDay)+"";
    }


    /**
     * 距离天数 yyyy-MM-dd
     * @param value
     * @return
     */
    @Filed(name = "距离天数[yyyy-MM-dd]", value = "arrivedDays")
    public String getArrivedDays(String value){
        Date birthday = DateUtil.convertString2Date(value);
        return DateUtil.diffDay(birthday, new Date())+"";
    }


    /**
     * 获取天气
     * @param value 省份|地区
     * @return
     */
    @Filed(name = "天气[省份|地区]", value = "weather")
    public Weather getWeather(String value){
        String[] strs = value.split("\\|");
        String province = strs[0];
        String city = strs[1];
        String url = String.format(environment.getProperty("weather.url"), province, city);
        String weatherDataJson = HttpUtil.get(url);
        Weather weather = JSON.parseObject(weatherDataJson, Weather.class);
        return weather;
    }


}
