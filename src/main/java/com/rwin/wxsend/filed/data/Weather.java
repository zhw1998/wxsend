package com.rwin.wxsend.filed.data;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rwin.wxsend.filed.FiledName;
import lombok.Data;

/**
 * 天气数据
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/19
 */

@Data
public class Weather extends FiledData {

    /**
     * {"city":"101210101","cityname":"杭州","fctime":"202212190800","temp":"8℃","tempn":"0℃","Weather":"晴","weathercode":"d0","weathercoden":"n0","wd":"无持续风向","ws":"<3级"}
     */


    /**
     * 城市
     */
    @FiledName(name = "城市")
    private String cityname;

    /**
     * 更新时间
     */
    @FiledName(name = "更新时间")
    private String fctime;

    /**
     * 最高温
     */
    @FiledName(name = "最高温")
    private String temp;

    /**
     * 最低温
     */
    @FiledName(name = "最低温")
    private String tempn;

    /**
     * 天气
     */
    @FiledName(name = "天气")
    private String weather;

    private String weathercode;

    private String weathercoden;


    /**
     * 风向
     */
    @FiledName(name = "风向")
    private String wd;

    /**
     * 风级
     */
    @FiledName(name = "风级")
    private String ws;



}
