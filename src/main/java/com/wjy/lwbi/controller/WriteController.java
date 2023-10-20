package com.wjy.lwbi.controller;

import com.wjy.lwbi.common.BaseResponse;
import com.wjy.lwbi.common.ErrorCode;
import com.wjy.lwbi.common.ResultUtils;
import com.wjy.lwbi.exception.BusinessException;
import com.wjy.lwbi.model.dto.write.GenWriteByAiRequest;
import com.wjy.lwbi.model.vo.WriteResponse;
import com.wjy.lwbi.service.WriteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@RestController
@RequestMapping("/write")
public class WriteController {

    @Resource
    private WriteService writeService;

    @PostMapping("/gen")
    @PreAuthorize("hasAnyAuthority('system:user:list')")
    public BaseResponse<WriteResponse> genWriteByAi(GenWriteByAiRequest genWriteByAiRequest, HttpServletRequest request) {
        if(genWriteByAiRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求数据异常");
        }
        WriteResponse writeResponse = writeService.genWriteByAi(genWriteByAiRequest, request);
        return ResultUtils.success(writeResponse);
    }

}
