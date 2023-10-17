package com.wjy.lwbi.model.dto.report;

import lombok.Data;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@Data
public class GenReportByAiRequest {

    /**
     * 修改要求
     */
    private String goal;

    /**
     * 报告名称
     */
    private String name;

    /**
     * 报告类型
     */

    private String reportType;

    private static final long serialVersionUID = 1L;

}
