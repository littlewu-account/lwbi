package com.wjy.lwbi.controller;

import com.wjy.lwbi.common.BaseResponse;
import com.wjy.lwbi.common.ResultUtils;
import com.wjy.lwbi.model.dto.chart.GenChartByAiRequest;
import com.wjy.lwbi.model.dto.report.GenReportByAiRequest;
import com.wjy.lwbi.model.vo.ReportAdviceResponse;
import com.wjy.lwbi.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@RestController
@RequestMapping("/report")
@Slf4j
public class ReportController {

    @Resource
    private ReportService reportService;

    @PostMapping("/gen")
    @PreAuthorize("hasAnyAuthority('system:report:list')")
    public BaseResponse<ReportAdviceResponse> genReportAdviceByAi(@RequestPart("file") MultipartFile multipartFile,
                                                                    GenReportByAiRequest genReportByAiRequest, HttpServletRequest request) {
        ReportAdviceResponse reportAdviceResponse = reportService.genReportAdviceByAi(multipartFile, genReportByAiRequest, request);
        return ResultUtils.success(reportAdviceResponse);
    }

}
