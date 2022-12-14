package com.rwin.wxsend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *  模板配置实体
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */

@Data
@TableName("model_config")
public class ModelConfig {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 本系统模板
     */
    private Integer modelId;

    /**
     * 基础配置
     */
    private Integer configId;

    /**
     * 公众号模板id
     */
    private String modelCode;

    /**
     * 具体模板内容 json
     */
    private String filedContent;

}
