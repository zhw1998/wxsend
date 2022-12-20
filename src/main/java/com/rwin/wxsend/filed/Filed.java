package com.rwin.wxsend.filed;

import java.lang.annotation.*;

/**
 * 实现参数的已注解
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Filed {

    String name() default "";

    String value() default "";


}
