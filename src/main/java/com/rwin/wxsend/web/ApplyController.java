package com.rwin.wxsend.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rwin.wxsend.dao.mapper.ApplyCodeMapper;
import com.rwin.wxsend.dao.mapper.ConfigMapper;
import com.rwin.wxsend.entity.ApplyCode;
import com.rwin.wxsend.entity.Config;
import com.rwin.wxsend.entity.dto.MsgDto;
import com.rwin.wxsend.entity.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 【这里填充类的作用说明】
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */
@Controller
@RequestMapping(value = "/apply")
@Slf4j
public class ApplyController {

    @Autowired
    private ApplyCodeMapper applyCodeMapper;
    @Autowired
    private ConfigMapper configMapper;

    /***
     * 查询
     *
     * @param request
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/5
     */
    @RequestMapping(value = "/searchByApplyCode", method = RequestMethod.POST)
    @ResponseBody
    public Result searchByApplyCode(HttpServletRequest request, String applyCode){
        if(applyCode == null){
            //不存在
            return new Result(302, "参数不能为空");
        }
        MsgDto result = new MsgDto();
        //根据申请码查询基本配置
        Config query = new Config();
        query.setApplyCode(applyCode);
        Config config = configMapper.selectOne(new QueryWrapper<>(query));

        return null;
    }


    /***
     * 新增
     *
     * @param request
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/5
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(HttpServletRequest request, MsgDto msgDto){
        //验证申请码是否存在并且未使用
        ApplyCode query = new ApplyCode();
        query.setCode(msgDto.getApplyCode());
        ApplyCode applyCode = applyCodeMapper.selectOne(new QueryWrapper<>(query));
        if(applyCode == null){
            //不存在
            return new Result(301, "申请码错误");
        }
        //保存

        return null;
    }



}
