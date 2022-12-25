package com.rwin.wxsend.web;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rwin.wxsend.dao.mapper.ModelMapper;
import com.rwin.wxsend.entity.Model;
import com.rwin.wxsend.entity.dto.Result;
import com.rwin.wxsend.filed.FiledMethod;
import com.rwin.wxsend.filter.AccessLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 模板配置
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */
@Controller
@RequestMapping(value = "/model")
@Slf4j
public class ModelController {

    @Autowired
    private ModelMapper modelMapper;



    /**
     * 保存
     * 
     * @param request	
     * @param model
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/13
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @AccessLimit(seconds = 1, maxCount = 3)
    public Result save(HttpServletRequest request, @RequestBody Model model){
        int r = 0;
        if(model.getName() == null || model.getTemplateContent() == null ){
            return new Result(Result.Param_notnull_code, Result.Param_notnull_msg);
        }
        if (model.getId() != null){
            //修改
            r = modelMapper.updateById(model);
        }else {
            //添加
            r = modelMapper.insert(model);
        }
        return r == 0 ? new Result(203, "保存失败") : new Result(model.getId());
    }

    /***
     * 模板列表查询
     * 
     * @param request	
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/13
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    @AccessLimit(seconds = 1, maxCount = 3)
    public Result list(HttpServletRequest request){
        List<Model> models = modelMapper.selectList(null);
        return new Result(models);
    }


    /***
     * 模板删除
     *
     * @param request
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/13
     */
    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    @AccessLimit(seconds = 1, maxCount = 3)
    public Result delete(HttpServletRequest request, Integer id){
        if(id == null){
            return new Result(Result.Param_notnull_code, Result.Param_notnull_msg);
        }
        int r = modelMapper.deleteById(id);
        return new Result(null);
    }


    /***
     * 根据名称查询模板
     *
     * @param request
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/13
     */
    @RequestMapping(value = "searchByName", method = RequestMethod.GET)
    @ResponseBody
    @AccessLimit(seconds = 1, maxCount = 3)
    public Result searchByName(HttpServletRequest request, String name){
        Model query = new Model();
        query.setName(name);
        List<Model> models = modelMapper.selectList(new QueryWrapper<>(query));
        return new Result(CollectionUtil.isEmpty(models)? null : models.get(0));
    }



    /**
     * 获取已实现的字段数据
     * 
     * @param request	
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/16
     */
    @RequestMapping(value = "listFiled", method = RequestMethod.GET)
    @ResponseBody
    @AccessLimit(seconds = 1, maxCount = 3)
    public Result listFiled(HttpServletRequest request){
        return new Result(FiledMethod.filedMap);
    }



}
