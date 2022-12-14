package com.rwin.wxsend.entity.dto;

import lombok.Data;

/**
 * 接口返回实体
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */

@Data
public class Result {

    private int code;

    private String msg;

    private Object data;

    public Result(){}

    public Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(Object data){
        this.code = 200;
        this.msg = "成功";
        this.data = data;
    }
}
