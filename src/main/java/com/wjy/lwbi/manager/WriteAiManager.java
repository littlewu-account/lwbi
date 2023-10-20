package com.wjy.lwbi.manager;

import com.wjy.lwbi.common.ErrorCode;
import com.wjy.lwbi.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@Service
public class WriteAiManager {

    @Resource
    private YuCongMingClient yuCongMingClient;

    public String doChat(Long modelId, String message) {
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        if(response == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Ai响应错误");
        }
        DevChatResponse data = response.getData();
        if(data == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Ai响应错误");
        }
        String content = data.getContent();
        return content;
    }

}
