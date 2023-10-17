package com.wjy.lwbi.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报告信息表
 * @TableName report
 */
@TableName(value ="report")
@Data
public class Report implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 修改要求
     */
    private String goal;

    /**
     * 报告名称
     */
    private String name;

    /**
     * 报告内容
     */
    private String reportData;

    /**
     * 报告类型
     */
    private String reportType;

    /**
     * 生成的修改意见
     */
    private String genResult;

    /**
     * wait,running,succeed,failed
     */
    private String status;

    /**
     * 执行信息
     */
    private String execMessage;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}