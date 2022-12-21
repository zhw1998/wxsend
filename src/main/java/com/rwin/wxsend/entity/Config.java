package com.rwin.wxsend.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置实体
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */

@Data
@TableName("config")
public class Config {

    //关闭
    public final static Integer Status_close = 0;

    //开启
    public final static Integer Status_open = 1;



    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 申请码
     */
    private String applyCode;

    /**
     * 微信公众号 appId
     */
    private String appId;

    /**
     * 微信公众号 appSecret
     */
    private String appSecret;

    /**
     * 发送方式策略
     */
    private String way;

    /**
     * 策略值
     */
    private String wayValue;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 状态 0-关闭 1-开启
     */
    private Integer status;


}
