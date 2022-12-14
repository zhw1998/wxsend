package com.rwin.wxsend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 消息模板实体
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */
@Data
@TableName("model")
public class Model {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 字段内容 json
     * [{"key":"birthDate","name":"生日","value":"2022-10-10"}]
     */
    private String filedContent;

    /**
     * 示例图
     */
    private String imgSrc;

}
