package com.rwin.wxsend.entity.dto;

import lombok.Data;

/**
 * 日期数据dto
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/16
 */
@Data
public class DateDto {


    public final static String IsWork_yes = "1";

    public final static String IsWork_no = "0";


    /**
     * 日期
     */
    private String date;

    /**
     * 星期
     */
    private String week;

    /**
     * 是否为工作日
     */
    private String isWork;

    /**
     * 日期描述
     */
    private String desc;


}
