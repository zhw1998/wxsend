package com.rwin.wxsend.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rwin.wxsend.dao.mapper.ApplyCodeMapper;
import com.rwin.wxsend.dao.mapper.ConfigMapper;
import com.rwin.wxsend.dao.mapper.ModelConfigMapper;
import com.rwin.wxsend.dao.mapper.SendUserMapper;
import com.rwin.wxsend.entity.ApplyCode;
import com.rwin.wxsend.entity.Config;
import com.rwin.wxsend.entity.ModelConfig;
import com.rwin.wxsend.entity.SendUser;
import com.rwin.wxsend.entity.dto.ConfigDto;
import com.rwin.wxsend.entity.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 【这里填充类的作用说明】
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */
@Controller
@RequestMapping(value = "/config")
@Slf4j
public class ConfigController {

    @Autowired
    private ApplyCodeMapper applyCodeMapper;
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private ModelConfigMapper modelConfigMapper;
    @Autowired
    private SendUserMapper sendUserMapper;

    /***
     * 查询
     *
     * @param request
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/5
     */
    @RequestMapping(value = "/searchByApplyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result searchByApplyCode(HttpServletRequest request, String applyCode){
        if(applyCode == null){
            return new Result(Result.Param_notnull_code, Result.Param_notnull_msg);
        }
        //根据申请码查询基本配置
        Config query = new Config();
        query.setApplyCode(applyCode);
        List<Config> configs = configMapper.selectList(new QueryWrapper<>(query));
        if(CollectionUtil.isEmpty(configs)){
            return new Result(null);
        }
        ConfigDto configDto = new ConfigDto();
        Config config = configs.get(0);
        BeanUtil.copyProperties(config, configDto);
        //查询模板配置信息
        ModelConfig modelConfigQuery = new ModelConfig();
        modelConfigQuery.setConfigId(config.getId());
        ModelConfig modelConfig = modelConfigMapper.selectOne(new QueryWrapper<>(modelConfigQuery));
        configDto.setModelConfig(modelConfig);
        //查询发送人员信息
        SendUser sendUserQuery = new SendUser();
        sendUserQuery.setConfigId(config.getId());
        List<SendUser> sendUsers = sendUserMapper.selectList(new QueryWrapper<>(sendUserQuery));
        configDto.setSendUsers(sendUsers);
        return new Result(configDto);
    }


    /***
     * 新增
     *
     * @param request
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/5
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(HttpServletRequest request, @RequestBody ConfigDto configDto){
        //验证申请码是否存在并且未使用
        ApplyCode query = new ApplyCode();
        query.setCode(configDto.getApplyCode());
        ApplyCode applyCode = applyCodeMapper.selectOne(new QueryWrapper<>(query));
        if(applyCode == null){
            //不存在
            return new Result(Result.Apply_code_error_code, Result.Apply_code_error_msg);
        }
        Config config = new Config();
        BeanUtil.copyProperties(configDto, config);
        if(configDto.getId() != null){
            //更新
            int r = configMapper.updateById(config);
            //保存模板配置信息
            ModelConfig modelConfig = configDto.getModelConfig();
            r = modelConfigMapper.updateById(modelConfig);
            //保存发送人员信息 先删除 后添加
            SendUser sendUserQuery = new SendUser();
            sendUserQuery.setConfigId(config.getId());
            r = sendUserMapper.delete(new QueryWrapper<>(sendUserQuery));
            List<SendUser> sendUsers = configDto.getSendUsers();
            sendUsers.stream().forEach(sendUser -> {
                sendUser.setId(null);
                sendUserMapper.insert(sendUser);
            });
        }else {
            //新增
            int r = configMapper.insert(config);
            //保存模板配置信息
            ModelConfig modelConfig = configDto.getModelConfig();
            r = modelConfigMapper.insert(modelConfig);
            //添加
            List<SendUser> sendUsers = configDto.getSendUsers();
            sendUsers.stream().forEach(sendUser -> {
                sendUser.setId(null);
                sendUserMapper.insert(sendUser);
            });
        }
        return new Result(config.getId());
    }


    /***
     * 获取发送策略
     *
     * @param
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/16
     */
    @RequestMapping(value = "getWay", method = RequestMethod.GET)
    @ResponseBody
    public Result getSendWay(HttpServletRequest request){
        return new Result(Config.Way_map);
    }

}
