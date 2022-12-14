package com.rwin.wxsend.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 配置类
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */

@Configuration
@MapperScan("com.rwin.wxsend.dao.mapper")
public class MpConfig {



}
