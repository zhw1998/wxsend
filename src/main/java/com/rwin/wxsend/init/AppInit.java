package com.rwin.wxsend.init;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import com.rwin.wxsend.entity.dto.DateDto;
import com.rwin.wxsend.filed.Filed;
import com.rwin.wxsend.filed.FiledMethod;
import com.rwin.wxsend.filed.FiledName;
import com.rwin.wxsend.filed.data.FiledData;
import com.rwin.wxsend.util.ClassUtil;
import com.rwin.wxsend.util.FiledDataUtil;
import com.rwin.wxsend.util.SpringUtil;
import com.rwin.wxsend.util.WayFilterUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目初始化
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/16
 */
@Component
@Log4j2
public class AppInit implements ApplicationRunner, ApplicationContextAware {

    
    
    /**
     * 启动之后执行
     * 
     * @param args	
     * @return void
     * @author zouhongwei
     * @date 2022/12/20
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        setDate();
        setWayMap();
        setFiledMap();
    }


    /**
     * Bean在被初始之后
     *
     * @param applicationContext
     * @return void
     * @author zouhongwei
     * @date 2022/12/20
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //容器初始化
        SpringUtil.initContext((WebApplicationContext) applicationContext);
    }

    /**
     * 从date.json获取日期数据
     *
     * @param
     * @return void
     * @author zouhongwei
     * @date 2022/12/16
     */
    public void setDate(){
        try {
            Resource resource = new ClassPathResource("date.json");
            if(resource != null){
                BufferedReader bufferedReader = resource.getReader(CharsetUtil.CHARSET_UTF_8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine())!=null) {
                    sb.append(line);
                }
                List<DateDto> dateDtos = JSON.parseArray(sb.toString(), DateDto.class);
                WayFilterUtil.setDateList(dateDtos);
                Map<String, DateDto> dateDtoMap = new HashMap<>();
                for (DateDto dateDto : dateDtos){
                    dateDtoMap.put(dateDto.getDate(), dateDto);
                }
                WayFilterUtil.setDateDtoMap(dateDtoMap);
            }
        }catch (Exception e){
            log.error("日期数据初始化失败：{}", e);
        }

    }


    /**
     * 获取已实现的发送方式
     * 从 WayFilterUtil.java
     * @param 	
     * @return void
     * @author zouhongwei
     * @date 2022/12/20
     */
    public void setWayMap(){
        WayFilterUtil wayFilterUtil = SpringUtil.getBean(WayFilterUtil.class);
        Method[] methods = wayFilterUtil.getClass().getDeclaredMethods();
        for (Method method : methods){
            Filed filedData = method.getAnnotation(Filed.class);
            if(filedData != null){
                WayFilterUtil.Way_map.put(filedData.name(), filedData.value());
                WayFilterUtil.WayMethodMap.put(filedData.value(), method);
            }
        }
    }
    
    
    /**
     * 获取已实现的获取参数的方法
     * 从 FiledMethod.java
     *
     * @param
     * @return void
     * @author zouhongwei
     * @date 2022/12/20
     */
    public void setFiledMap(){
        FiledMethod filedMethod = SpringUtil.getBean(FiledMethod.class);
        //实现类的所有方法
        Method[] methods = filedMethod.getClass().getMethods();
        //获取@FiledData注解
        for (Method method : methods){
            Filed filedData = method.getAnnotation(Filed.class);
            if(filedData != null){
                //设置已实现方法
                FiledDataUtil.FiledMethodMap.put(filedData.value(), method);
                Class returnType = method.getReturnType();
                //如果返回值是对象
                if(ClassUtil.newInstant(returnType) instanceof FiledData){
                    //获取每个变量数据
                    Field[] fields = returnType.getDeclaredFields();
                    for (Field field : fields){
                        FiledName filedName = field.getAnnotation(FiledName.class);
                        if(filedName != null){
                            FiledMethod.filedMap.put(filedData.name() + "-" + filedName.name(), filedData.value() + "_" + field.getName());
                        }
                    }
                }else {
                    FiledMethod.filedMap.put(filedData.name(), filedData.value());
                }
            }
        }
    }

}
