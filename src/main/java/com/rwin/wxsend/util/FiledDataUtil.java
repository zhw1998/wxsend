package com.rwin.wxsend.util;

import com.rwin.wxsend.entity.dto.ModelFiled;
import com.rwin.wxsend.filed.FiledMethod;
import com.rwin.wxsend.filed.data.FiledData;
import lombok.extern.log4j.Log4j2;
import java.lang.reflect.Method;

/**
 * 字段数据获取工具类
 * 例如：
 *      简单字符串： arriveBirthdayDays
 *      同一对象的字符串：  Weather.temp    最高温
 *                      Weather.tempn    最低温
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/16
 */
@Log4j2
public class FiledDataUtil {


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
            FiledMethod filedMethod = SpringUtil.getBean(FiledMethod.class);
            String[] fileds = modelFiled.getKey().split("_");
            String methodName = String.format("get%s", StringUtil.toUpperCaseFirstOne(fileds[0]));
            Method method = filedMethod.getClass().getMethod(methodName, String.class);
            if(method == null){
                return modelFiled.getValue();
            }
            Object data = method.invoke(filedMethod, modelFiled.getValue());
            //返回的是对象
            if(data instanceof FiledData){
                String getFiledValueMethodName = String.format("get%s", StringUtil.toUpperCaseFirstOne(fileds[1]));
                Method getFiledValueMethod = data.getClass().getMethod(getFiledValueMethodName, null);
                return (String) getFiledValueMethod.invoke(data);
            }
            return (String) data;
        }catch (Exception e){
            log.info("获取参数【{}】数据失败:{}", modelFiled.getKey(), e);
            return modelFiled.getValue();
        }
    }












}
