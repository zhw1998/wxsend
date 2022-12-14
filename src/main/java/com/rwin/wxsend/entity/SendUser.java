package com.rwin.wxsend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 发送用户实体类
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */
@Data
@TableName("send_user")
public class SendUser {


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 配置关联id
     */
    private Integer configId;

    /**
     * 接受用户微信 openId
     */
    private String userOpenId;

    /**
     * 接受用户名称
     */
    private String userName;

}
