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

    public static final int Apply_code_error_code = 301;
    public static final String Apply_code_error_msg = "申请码错误";

    public static final int Param_notnull_code = 302;
    public static final String Param_notnull_msg = "参数不能为空";

    private int code;

    private String msg;

    private Object data;

    private boolean succ = false;

    public Result(){}

    public Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(Object data){
        this.code = 200;
        this.msg = "成功";
        this.succ = true;
        this.data = data;
    }
}
