package com.rwin.wxsend.util;

import com.rwin.wxsend.entity.dto.DateDto;
import com.rwin.wxsend.filed.Filed;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 发送策略判断工具类
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/16
 */
@Component
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
     * 日期
     */
    @Getter
    @Setter
    public static List<DateDto> dateList = new ArrayList<>();
    @Setter
    @Getter
    public static Map<String, DateDto> dateDtoMap = new HashMap<>();

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
    public boolean isAllow(String way, String wayValue, String sendTime, Date nowDate){
        if(!isInTime(sendTime, nowDate)){
            return false;
        }
        Method method = WayMethodMap.get(way);
        if(method == null){
            return true;
        }
        try {
            if(StringUtils.isEmpty(wayValue)){
                return (boolean) method.invoke(this, nowDate);
            }
            return (boolean) method.invoke(this, nowDate, wayValue);
        }catch (Exception e){
            return false;
        }
    }


    /**
     * 是否 在当前时间和 后5分钟（包括）内
     *
     * @param sendTime   HH:mm:ss
     * @param nowDate    当前时间
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    private boolean isInTime(String sendTime, Date nowDate){
        //获取当前时间 到 五分钟之后
        nowDate = DateUtil.convertString2Date(DateUtil.getCurrentStringDate("yyyy-MM-dd HH:mm") + ":00", DateUtil.LONG_DATE_FORMAT);
        Date afterDate = new Date(nowDate.getTime() + 5*60*1000);
        Date sendDate = DateUtil.convertString2Date( DateUtil.getCurrentStringDate()+ " " + sendTime + ":00", DateUtil.LONG_DATE_FORMAT);
        if(sendDate.compareTo(nowDate) > 0 && sendDate.compareTo(afterDate) <= 0){
            return true;
        }
        return false;
    }


    /**
     * 工作日
     *
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    @Filed(name = "工作日", value = "workday")
    private boolean workdayWay(Date nowDate){
        String date = DateUtil.convertDate2String(nowDate);
        DateDto dateDto = dateDtoMap.get(date);
        if(dateDto != null && DateDto.IsWork_yes.equals(dateDto.getIsWork())){
            return true;
        }
        return false;
    }

    /**
     * 休息日
     *
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    @Filed(name = "休息日", value = "offday")
    private boolean offdayWay(Date nowDate){
        String date = DateUtil.convertDate2String(nowDate);
        DateDto dateDto = dateDtoMap.get(date);
        if(dateDto != null && dateDto.getIsWork() == DateDto.IsWork_no){
            return true;
        }
        return false;
    }

    /**
     * 每周几
     *
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    @Filed(name = "每周:['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日']", value = "weekDay")
    private boolean isWeekDayWay(Date nowDate, String value){
        String date = DateUtil.convertDate2String(nowDate);
        DateDto dateDto = dateDtoMap.get(date);
        return dateDto.getWeek().equals(value);
    }

    /**
     * 每月几
     *
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    @Filed(name = "每月:['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31']", value = "monthDay")
    private boolean isMonthDayWay(Date nowDate, String value){
        Integer day = Integer.parseInt(value);
        return day.equals(nowDate.getDate());
    }


}
