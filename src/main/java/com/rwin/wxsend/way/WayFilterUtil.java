package com.rwin.wxsend.way;

import com.rwin.wxsend.util.ClassUtil;
import com.rwin.wxsend.util.DateUtil;
import com.rwin.wxsend.util.SpringUtil;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送策略判断工具类
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/16
 */
public class WayFilterUtil {

    /**
     * 发送策略
     */
    public static Map<String, String> Way_map = new HashMap<>();

    static {
        Way_map.put("每天", "everyday");
    }
    /**
     * 发送策略对应方法
     */
    public static Map<String, Method> WayMethodMap = new HashMap<>();



    /**
     * 是否允许发送
     *
     * @param way 发送策略
     * @param wayValue 策略值
     * @param nowDate 当前时间
     * @param sendTime 发送时间
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    public static boolean isAllow(String way, String wayValue, String sendTime, Date nowDate){
        if(!isInTime(sendTime, nowDate)){
            return false;
        }
        Method method = WayMethodMap.get(way);
        if(method == null){
            return true;
        }
        try {
            WayMethod wayMethod = SpringUtil.getBean(WayMethod.class);
            if(StringUtils.isEmpty(wayValue)){
                return (boolean) method.invoke(wayMethod, nowDate);
            }
            return (boolean) method.invoke(wayMethod, nowDate, wayValue);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    /**
     * 是否 在当前时间（包括） 和 后5分钟（不包括）内
     *
     * @param sendTime   HH:mm:ss
     * @param nowDate    当前时间
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    private static boolean isInTime(String sendTime, Date nowDate){
        //获取当前时间 到 五分钟之后
        nowDate = DateUtil.convertString2Date(DateUtil.getCurrentStringDate("yyyy-MM-dd HH:mm") + ":00", DateUtil.LONG_DATE_FORMAT);
        Date afterDate = new Date(nowDate.getTime() + 5*60*1000);
        Date sendDate = DateUtil.convertString2Date( DateUtil.getCurrentStringDate()+ " " + sendTime + ":00", DateUtil.LONG_DATE_FORMAT);
        if(sendDate.compareTo(nowDate) >= 0 && sendDate.compareTo(afterDate) < 0){
            return true;
        }
        return false;
    }




}
