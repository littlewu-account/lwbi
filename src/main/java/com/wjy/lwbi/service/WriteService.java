package com.wjy.lwbi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjy.lwbi.model.dto.write.GenWriteByAiRequest;
import com.wjy.lwbi.model.entity.Write;
import com.wjy.lwbi.model.vo.WriteResponse;

import javax.servlet.http.HttpServletRequest;


/**
* @author littlew
* @description 针对表【write(创作信息表)】的数据库操作Service
* @createDate 2023-10-19 20:10:34
*/
public interface WriteService extends IService<Write> {

    WriteResponse genWriteByAi(GenWriteByAiRequest genWriteByAiRequest, HttpServletRequest request);
}
