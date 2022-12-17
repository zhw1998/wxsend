package com.rwin.wxsend.task;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rwin.wxsend.dao.mapper.ConfigMapper;
import com.rwin.wxsend.entity.Config;
import com.rwin.wxsend.util.WayFilterUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时执行任务
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 * 多线程任务
 * @author zouhongwei
 * @create 2022/12/5
 */
@Component
@EnableScheduling
@EnableAsync
@Log4j2
public class SendMsgTask {

    @Autowired
    private ConfigMapper configMapper;


    /***
     * 定时执行消息推送任务 异步执行
     * 5分钟执行一次
     * 
     * @param 	
     * @return void
     */
    @Async
    @Scheduled(fixedDelay = 5*60*1000)
    public void run(){

//        //获取当前时间 HH-mm-ss
//        Date nowDate = new Date();
//        //查询开启的配置信息
//        Config query = new Config();
//        query.setStatus(Config.Status_open);
//        List<Config> configList = configMapper.selectList(new QueryWrapper<>(query));
//        //过滤需要发送的消息
//        List<Config> sendConfigs = configList.stream().filter(config -> {
//            return WayFilterUtil.isAllow(config.getWay(), config.getSendTime(), nowDate);
//        }).collect(Collectors.toList());
//        if(CollectionUtil.isEmpty(sendConfigs)) {
//            return;
//        };
//        //消息发送
//        sendMsg(sendConfigs);
    }

    /***
     * 消息发送
     * 
     * @param configs	
     * @return void
     * @author zouhongwei
     * @date 2022/12/16
     */
    private void sendMsg(List<Config> configs){
        //初始化参数

        //请求微信接口
    }

}
