package com.rwin.wxsend.entity.dto;

import com.rwin.wxsend.entity.Config;
import com.rwin.wxsend.entity.ModelConfig;
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
public class ConfigDto extends Config {

    /**
     * 待发送用户
     */
    private List<SendUser> sendUsers;

    /**
     * 模板配置详情
     */
    private ModelConfig modelConfig;


}
