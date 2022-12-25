package com.rwin.wxsend.task;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rwin.wxsend.dao.mapper.ConfigMapper;
import com.rwin.wxsend.dao.mapper.ModelConfigMapper;
import com.rwin.wxsend.dao.mapper.SendUserMapper;
import com.rwin.wxsend.entity.Config;
import com.rwin.wxsend.entity.ModelConfig;
import com.rwin.wxsend.entity.SendUser;
import com.rwin.wxsend.entity.dto.ModelFiled;
import com.rwin.wxsend.filed.FiledDataUtil;
import com.rwin.wxsend.way.WayFilterUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private ModelConfigMapper modelConfigMapper;
    @Autowired
    private SendUserMapper sendUserMapper;

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
        //获取当前时间 HH-mm-ss
        Date nowDate = new Date();
        //查询开启的配置信息
        Config query = new Config();
        query.setStatus(Config.Status_open);
        List<Config> configList = configMapper.selectList(new QueryWrapper<>(query));
        //过滤需要发送的消息
        List<Config> sendConfigs = configList.stream().filter(config -> {
            return WayFilterUtil.isAllow(config.getWay(), config.getWayValue(), config.getSendTime(), nowDate);
        }).collect(Collectors.toList());
        if(CollectionUtil.isEmpty(sendConfigs)) {
            return;
        };
        //消息发送
        sendMsg(sendConfigs);
    }

    /**
     * 消息发送
     * 
     * @param configs	
     * @return void
     * @author zouhongwei
     * @date 2022/12/16
     */
    private void sendMsg(List<Config> configs){
        log.info("发送消息：{}", configs);
        configs.stream().forEach(config -> {
            try {
                //获取模板参数
                ModelConfig modelConfigQuery = new ModelConfig();
                modelConfigQuery.setConfigId(config.getId());
                List<ModelConfig> modelConfigList = modelConfigMapper.selectList(new QueryWrapper<>(modelConfigQuery));
                //目前只支持一个模板
                ModelConfig modelConfig = modelConfigList.get(0);
                //初始化参数
                String filedContent = modelConfig.getFiledContent();
                List<ModelFiled> modelFiledList = JSON.parseArray(filedContent, ModelFiled.class);
                Map<String, Map<String, String>> dataMap = new HashMap<>();
                modelFiledList.stream().forEach(modelFiled -> {
                    String value = FiledDataUtil.getByFiled(modelFiled);
                    // 接口参数名称
                    String paramName = modelFiled.getKey();
                    if(dataMap.get(paramName) != null){
                        /**
                         *  相同参数名处理方式:
                         *     按循序：birthday  birthday[1]  birthday[2] 依次类推
                         */
                        //获取相同参数名的序数
                        for (int i = 1; i <= dataMap.keySet().size(); i++){
                            paramName = String.format(paramName + "[%d]", i);
                            if(dataMap.get(paramName) == null){
                                break;
                            }
                        }
                    }
                    Map<String, String> param = new HashMap<>();
                    param.put("value", value);
                    param.put("color", modelFiled.getColor());
                    dataMap.put(paramName, param);
                });
                //获取发送用户
                SendUser sendUserQuery = new SendUser();
                sendUserQuery.setConfigId(config.getId());
                List<SendUser> sendUserList = sendUserMapper.selectList(new QueryWrapper<>(sendUserQuery));
                sendUserList.stream().forEach(sendUser -> {
                    //请求微信接口参数组装
                    Map<String, Object> data = new HashMap<>();
                    data.put("touser", sendUser.getUserOpenId());
                    data.put("template_id", modelConfig.getModelCode());
                    data.put("url", "http://weixin.qq.com/download");
                    data.put("data", dataMap);
                    //发送请求
                    String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", getAccessToken(config));
                    String rsJson = HttpUtil.post(url, JSON.toJSONString(data));
                    log.info("发送参数:{}", JSON.toJSONString(data));
                    log.info("【configId={}】发送给【sendUserId={}】结果:{}", config.getId(), sendUser.getId(), rsJson);
                });
            }catch (Exception e){
                log.error("【configId={}】消息发送失败:{}", config.getId(), e);
            }
        });
    }


    /**
     * 获取token
     *
     * @param config
     * @return java.lang.String
     * @author zouhongwei
     * @date 2022/12/19
     */
    private String getAccessToken(Config config){
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", config.getAppId(), config.getAppSecret());
        String resJson = HttpUtil.get(url);
        Map<String, String> resMap = (Map<String, String>) JSON.parse(resJson);
        return resMap.get("access_token");
    }

}
