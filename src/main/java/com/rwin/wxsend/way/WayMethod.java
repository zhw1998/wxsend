package com.rwin.wxsend.way;

import com.rwin.wxsend.entity.dto.DateDto;
import com.rwin.wxsend.filed.Filed;
import com.rwin.wxsend.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 发送策略实现方法
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/22
 */
@Component
public class WayMethod {

    /**
     * 日期
     */
    public static List<DateDto> dateList = new ArrayList<>();

    public static Map<String, DateDto> dateDtoMap = new HashMap<>();


    /**
     * 工作日
     *
     * @return boolean
     * @author zouhongwei
     * @date 2022/12/16
     */
    @Filed(name = "工作日", value = "workday")
    public boolean workdayWay(Date nowDate){
        System.out.println(nowDate);
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
    public boolean offdayWay(Date nowDate){
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
    public boolean isWeekDayWay(Date nowDate, String value){
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
    public boolean isMonthDayWay(Date nowDate, String value){
        Integer day = Integer.parseInt(value);
        return day.equals(nowDate.getDate());
    }

}
