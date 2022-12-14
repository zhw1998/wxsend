package com.rwin.wxsend.task;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
public class SendMsgTask {

    /***
     * 定时执行消息推送任务
     * 一分钟执行一次
     * 
     * @param 	
     * @return void
     */
    @Async
    @Scheduled(fixedDelay = 60*1000)
    public void run(){
        //新建线程
        //查询当前需要推送的信息
        //消息发送
    }

    //消息发送
    private void sendMsg(){
        //
        //初始化参数
        //请求微信接口
    }


}
