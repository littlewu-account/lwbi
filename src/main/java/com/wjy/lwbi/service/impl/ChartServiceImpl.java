package com.wjy.lwbi.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.lwbi.bizmq.BiMessageProducer;
import com.wjy.lwbi.common.ErrorCode;
import com.wjy.lwbi.constant.CommonConstant;
import com.wjy.lwbi.exception.ThrowUtils;
import com.wjy.lwbi.manager.RedissonManager;
import com.wjy.lwbi.mapper.ChartMapper;
import com.wjy.lwbi.model.dto.chart.GenChartByAiRequest;
import com.wjy.lwbi.model.entity.Chart;
import com.wjy.lwbi.model.entity.User;
import com.wjy.lwbi.model.vo.BiResponse;
import com.wjy.lwbi.service.ChartService;
import com.wjy.lwbi.service.UserService;
import com.wjy.lwbi.utils.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
* @author littlew
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-08-13 11:22:08
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService {

    @Resource
    private UserService userService;

    @Resource
    private RedissonManager redissonManager;

    @Resource
    private BiMessageProducer biMessageProducer;

    public BiResponse genChartByAiMq(MultipartFile multipartFile, GenChartByAiRequest genChartByAiRequest
            , HttpServletRequest request) {
        String name = genChartByAiRequest.getName();
        String goal = genChartByAiRequest.getGoal();
        String chartType = genChartByAiRequest.getChartType();
        //校验
        ThrowUtils.throwIf(StringUtils.isBlank(goal), ErrorCode.PARAMS_ERROR, "目标为空");
        ThrowUtils.throwIf(StringUtils.isBlank(name) && name.length() > 100, ErrorCode.PARAMS_ERROR, "名称为空或过长");
        //用户信息
        User loginUser = userService.getLoginUser(request);
        //限流
        redissonManager.doRateLimit("genChartByAi_" + loginUser.getId());
        //校验文件
        long size = multipartFile.getSize();
        String originalFilename = multipartFile.getOriginalFilename();
        //1. 校验文件大小
        final long ONE_MB = 1024 * 1024L;
        ThrowUtils.throwIf(size > ONE_MB, ErrorCode.PARAMS_ERROR, "文件超过1M");
        //2. 校验文件名后缀
        String suffix = FileUtil.getSuffix(originalFilename);
        List<String> suffixes = Arrays.asList("xlsx", "xls");
        ThrowUtils.throwIf(!suffixes.contains(suffix), ErrorCode.PARAMS_ERROR, "上传文件类型错误，请重新上传");
        String result = ExcelUtils.excelToCsv(multipartFile);
        //执行AI调用之前，先把信息保存在数据库中
        Chart chart = new Chart();
        chart.setGoal(goal);
        chart.setName(name);
        chart.setChartData(result);
        chart.setChartType(chartType);
        chart.setUserId(loginUser.getId());
        chart.setStatus("wait");
        boolean saveResult = this.save(chart);
        ThrowUtils.throwIf(!saveResult, ErrorCode.SYSTEM_ERROR, "图表信息保存失败");
        //调用ai接口
        long biModelId = CommonConstant.BI_MODEL_ID;
        //这里发送消息到RabbitMQ中
        Long newChartId = chart.getId();
        String message = String.valueOf(newChartId);
        biMessageProducer.sendMessage(message);

        BiResponse biResponse = new BiResponse();
        biResponse.setChartId(newChartId);
        return biResponse;
    }

}




