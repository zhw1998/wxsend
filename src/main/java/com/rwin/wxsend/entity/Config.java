package com.rwin.wxsend.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 申请id
     */
    private String applyCodeId;

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
     * 发送时间
     */
    private String sendTime;



}
