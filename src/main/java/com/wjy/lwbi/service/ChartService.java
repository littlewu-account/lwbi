package com.wjy.lwbi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjy.lwbi.model.dto.chart.GenChartByAiRequest;
import com.wjy.lwbi.model.entity.Chart;
import com.wjy.lwbi.model.vo.BiResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author littlew
* @description 针对表【chart(图表信息表)】的数据库操作Service
* @createDate 2023-08-13 11:22:08
*/
public interface ChartService extends IService<Chart> {
    public BiResponse genChartByAiMq(MultipartFile multipartFile, GenChartByAiRequest genChartByAiRequest
            , HttpServletRequest request);
}
