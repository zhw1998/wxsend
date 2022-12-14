package com.rwin.wxsend.entity.dto;

import com.rwin.wxsend.entity.SendUser;
import lombok.Data;

import java.util.List;

/**
 * 消息dto
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */

@Data
public class MsgDto{

    private String appId;

    private String appSecret;

    /**
     * 公众号消息模板
     */
    private String modelCode;

    /**
     * 申请码
     */
    private String applyCode;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 待发送用户
     */
    private List<SendUser> sendUsers;






}
