package com.rwin.wxsend.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rwin.wxsend.dao.mapper.ModelMapper;
import com.rwin.wxsend.entity.Model;
import com.rwin.wxsend.entity.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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



    /***
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
    public Result save(HttpServletRequest request, Model model){
        int r = 0;
        if(model.getName() == null || model.getTemplateContent() == null || model.getFiledContent() == null){
            return new Result(302, "参数不能为空");
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
    public Result list(HttpServletRequest request){
        List<Model> models = modelMapper.selectList(null);
        return new Result(models);
    }



    /***
     * 查询一条数据
     *
     * @param request
     * @param query
     * @return com.rwin.wxsend.entity.dto.Result
     * @author zouhongwei
     * @date 2022/12/13
     */
    @RequestMapping(value = "queryOne", method = RequestMethod.POST)
    @ResponseBody
    public Result queryOne(HttpServletRequest request, Model query){
        Model model = modelMapper.selectOne(new QueryWrapper<>(query));
        return new Result(model);
    }



    


}
