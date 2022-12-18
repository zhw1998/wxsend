package com.rwin.wxsend.util;

import com.rwin.wxsend.entity.dto.ModelFiled;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 字段数据获取工具类
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/16
 */
@Log4j2
public class FiledDataUtil {

    public static Map<String, String> filedMap = new HashMap<>();


    static {
        filedMap.put("距离生日天数", "arriveBirthdayDays");
        filedMap.put("天气", "weather");
    }

    /**
     * 根据字段获取数据
     *
     * @param modelFiled
     * @return java.lang.String
     * @author zouhongwei
     * @date 2022/12/16
     */
    public static String getByFiled(ModelFiled modelFiled){
        try {
            Class filedUtilClass = new FiledDataUtil().getClass();
            Method[] methods = filedUtilClass.getMethods();
            Method method = null;
            for (Method m : methods){
                String methodName = "get" + StringUtil.toUpperCaseFirstOne(modelFiled.getKey());
                if(methodName.equals(m.getName())){
                    method = m;
                }
            }
            if(method == null){
                return "";
            }
            return (String) method.invoke(filedUtilClass, modelFiled.getValue());
        }catch (Exception e){
            log.error("获取参数【{}】数据失败:{}", modelFiled.getKey(), e);
            return "";
        }
    }


    /**
     * 获取距离生日天数
     * @param value
     * @return
     */
    public String getArriveBirthdayDays(String value){
        Date birthday = DateUtil.convertString2Date(value);
        return DateUtil.diffDay(birthday, new Date())+"";
    }


    /**
     * 获取天气
     * @param city
     * @return
     */
    public String getWeather(String city){
        return "";
    }





    public static void main(String[] args) {
        Date birthday = DateUtil.convertString2Date("2023-12-1");
        System.out.print(DateUtil.diffDay(birthday, new Date()));
    }











}
