package com.rwin.wxsend.util;

import com.rwin.wxsend.entity.dto.DateDto;
import com.rwin.wxsend.filed.Filed;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * 发送策略判断工具类
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/16
 */
public class WayFilterUtil {


    //每天
    public final static int Way_everyday = 1;

    //工作日
    public final static int Way_workday = 2;

    //休息日
    public final static int Way_offday = 3;


    public static Map<String, Integer> Way_map = new HashMap<>();

    static {
        Way_map.put("每天", Way_everyday);
    }


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
     * @param sendTime 发送时间
     * @param nowDate 当前时间
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    public static boolean isAllow(Integer way, String sendTime, Date nowDate){
        if(!isInTime(sendTime, nowDate)){
            return false;
        }
        switch (way){
            case Way_everyday : return true;
            case Way_workday : return workdayWay(nowDate);
            case Way_offday : return offdayWay(nowDate);
            default: return false;
        }
    }


    /**
     * 是否 在当前时间前5分钟（不包括5分钟） 后5分钟（包括）内
     *
     * @param sendTime   HH:mm:ss
     * @param nowDate    当前时间
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    private static boolean isInTime(String sendTime, Date nowDate){
        //获取当前时间 到分钟
        nowDate = DateUtil.convertString2Date(DateUtil.getCurrentStringDate("yyyy-MM-dd HH:mm") + ":00", DateUtil.LONG_DATE_FORMAT);
        Date beforeDate = new Date(nowDate.getTime() - 5*60*1000);
        Date afterDate = new Date(nowDate.getTime() + 5*60*1000);
        Date sendDate = DateUtil.convertString2Date( DateUtil.getCurrentStringDate()+ " " + sendTime + ":00", DateUtil.LONG_DATE_FORMAT);
        if(sendDate.compareTo(beforeDate) > 0 && sendDate.compareTo(afterDate) <= 0){
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
    @Filed(name = "工作日", value = Way_workday+"")
    private static boolean workdayWay(Date nowDate){
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
    @Filed(name = "休息日", value = Way_offday+"")
    private static boolean offdayWay(Date nowDate){
        String date = DateUtil.convertDate2String(nowDate);
        DateDto dateDto = dateDtoMap.get(date);
        if(dateDto != null && dateDto.getIsWork() == DateDto.IsWork_no){
            return true;
        }
        return false;
    }

}
