package com.rwin.wxsend.entity.dto;

import lombok.Data;

/**
 * 模板通用对应类
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */

@Data
public class ModelFiled {

    /**
     * 参数
     */
    private String key;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数值
     */
    private String value;
    /**
     * 颜色
     */
    private String color;
}
