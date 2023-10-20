package com.wjy.lwbi.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创作信息表
 * @TableName write
 */
@TableName(value ="`write`")
@Data
public class Write implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文案主题
     */
    private String topic;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 文案名称
     */
    private String name;

    /**
     * 文案类型
     */
    private String articleType;

    /**
     * 文案场景
     */
    private String articleScene;

    /**
     * 生成的报告内容
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