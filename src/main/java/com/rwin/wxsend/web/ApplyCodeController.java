package com.rwin.wxsend.web;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rwin.wxsend.dao.mapper.ApplyCodeMapper;
import com.rwin.wxsend.entity.ApplyCode;
import com.rwin.wxsend.entity.dto.Result;
import com.rwin.wxsend.util.SerializerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 申请码
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */
@Controller
@RequestMapping(value = "/applyCode")
@Slf4j
public class ApplyCodeController {


    @Autowired
    private ApplyCodeMapper applyCodeMapper;



    /**
     * 查询申请数据列表
     * 
     * @param request	
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/16
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(HttpServletRequest request){
        List<ApplyCode> applyCodes = applyCodeMapper.selectList(null);
        return new Result(SerializerUtil.serialize(applyCodes) );
    }



    /**
     *  创建申请码
     *
     * @param request
     * @param num
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/16
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @ResponseBody
    public Result create(HttpServletRequest request, int num){
        if(num == 0){
            return new Result(null);
        }
        List<String> applyCodes = new ArrayList<>();
        //随机创建num个申请码
        for(int i = 0; i < num; i++){
            String uuid = IdUtil.fastUUID();
            ApplyCode applyCode = new ApplyCode();
            applyCode.setCode(uuid);
            applyCode.setCreateTime(new Date());
            applyCode.setStatus(ApplyCode.Status_default);
            int r = applyCodeMapper.insert(applyCode);
            if(r > 0){
                applyCodes.add(uuid);
            }
        }
        return new Result(applyCodes);
    }

}
