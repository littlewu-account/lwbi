package com.wjy.lwbi.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.lwbi.common.ErrorCode;
import com.wjy.lwbi.constant.CommonConstant;
import com.wjy.lwbi.exception.BusinessException;
import com.wjy.lwbi.manager.RedissonManager;
import com.wjy.lwbi.manager.ReportAiManager;
import com.wjy.lwbi.mapper.ReportMapper;
import com.wjy.lwbi.model.dto.report.GenReportByAiRequest;
import com.wjy.lwbi.model.entity.Report;
import com.wjy.lwbi.model.entity.User;
import com.wjy.lwbi.model.vo.ReportAdviceResponse;
import com.wjy.lwbi.service.ReportService;
import com.wjy.lwbi.service.UserService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    @Resource
    private UserService userService;

    @Resource
    private RedissonManager redissonManager;

    @Resource
    private ReportAiManager reportAiManager;


    @Override
    public ReportAdviceResponse genReportAdviceByAi(MultipartFile multipartFile, GenReportByAiRequest genReportByAiRequest, HttpServletRequest request) {
        //获取请求信息
        String goal = genReportByAiRequest.getGoal();
        String reportType = genReportByAiRequest.getReportType();
        String reportName = genReportByAiRequest.getName();

        //请求信息校验
        if(!StringUtils.hasText(goal) || !StringUtils.hasText(reportType) || !StringUtils.hasText(reportName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode(), "请求数据异常");
        }
        //校验文件（大小、后缀）
        String originalFilename = multipartFile.getOriginalFilename();
        long size = multipartFile.getSize();
        final long ONE_MB = 1024 * 1024L;
        if(size > ONE_MB) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode(), "文件过大");
        }
        String suffix = FileUtil.getSuffix(originalFilename);
        List<String> suffixList = Arrays.asList("doc", "docx");
        if(!suffixList.contains(suffix)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR.getCode(), "文件格式不支持");
        }

        //获取当前用户
        User loginUser = userService.getLoginUser(request);
        Long id = loginUser.getId();

        //用户限流
        String limitKey = "genReportAdviceByAi" + id;
        redissonManager.doRateLimit(limitKey);

        //根据用户传来的数据，拼接修改请求
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("以下是" + reportType + "报告内容，" + goal + ":");
        stringBuilder.append("\n");
        StringBuilder reportContentStringBuilder = new StringBuilder();
        //拼接报告内容
        try {
            InputStream inputStream = multipartFile.getInputStream();
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
            paragraphs.stream().forEach(p -> {
                reportContentStringBuilder.append(p.getText());
                reportContentStringBuilder.append("\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR.getCode(), "文件读取错误");
        }
        String reportContent = reportContentStringBuilder.toString();
        stringBuilder.append(reportContent);

        //stringBuilder.append()
        String reviseRequest = stringBuilder.toString();

        //调用AI接口，生成修改建议
        String reviseResponse = reportAiManager.doChat(CommonConstant.Report_MODEL_ID, reviseRequest);

        //创建Report对象，存入数据库
        Report report = new Report();
        report.setGoal(goal);
        report.setGenResult(reviseResponse);
        report.setName(reportName);
        report.setReportType(reportType);
        report.setUserId(id);
        report.setReportData(reportContent);
        report.setStatus("完成");
        boolean saveResult = this.save(report);
        if(!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR.getCode(), "报告信息保存失败");
        }

        //将AI返回的修改建议封装进ReportAdviceResponse并返回
        ReportAdviceResponse reportAdviceResponse = new ReportAdviceResponse();
        reportAdviceResponse.setReportId(report.getId());
        reportAdviceResponse.setGenResult(reviseResponse);
        return reportAdviceResponse;
    }
}
