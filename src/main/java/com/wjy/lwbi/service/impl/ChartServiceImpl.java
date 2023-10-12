package com.wjy.lwbi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.lwbi.mapper.ChartMapper;
import com.wjy.lwbi.model.entity.Chart;
import com.wjy.lwbi.service.ChartService;
import org.springframework.stereotype.Service;

/**
* @author littlew
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-08-13 11:22:08
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService {

}




