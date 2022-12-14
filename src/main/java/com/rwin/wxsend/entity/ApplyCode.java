package com.rwin.wxsend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 申请码实体类
 * Copyright (C), 2016-2022, 杭州世平信息科技有限公司
 *
 * @author zouhongwei
 * @create 2022/12/5
 */

@Data
@TableName("apply_code")
public class ApplyCode {

    public final static Integer Status_default = 0;

    public final static Integer Status_used = 1;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 申请用户ip
     */
    private String ip;

    /**
     * 申请码
     */
    private String code;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 使用时间
     */
    private Date usedTime;

    /**
     * 状态 0-未使用  1-已使用
     */
    private Integer status;

}
