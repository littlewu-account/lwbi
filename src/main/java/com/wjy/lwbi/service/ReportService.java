package com.wjy.lwbi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjy.lwbi.model.dto.report.GenReportByAiRequest;
import com.wjy.lwbi.model.entity.Report;
import com.wjy.lwbi.model.vo.ReportAdviceResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
* @author littlew
* @description 针对表【report(报告信息表)】的数据库操作Service
* @createDate 2023-10-16 18:01:31
*/
public interface ReportService extends IService<Report> {

    ReportAdviceResponse genReportAdviceByAi(MultipartFile multipartFile, GenReportByAiRequest genReportByAiRequest, HttpServletRequest request);
}
