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

// todo 这种Manager类，可以利用工厂方法模式，对doChat方法进行抽象
@Service
public class ReportAiManager {

    @Resource
    private YuCongMingClient yuCongMingClient;

    public String doChat(long modelId, String message) {
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(message);

        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        if(response == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR.getCode(), "Ai响应异常");
        }
        DevChatResponse devChatResponse = response.getData();
        String content = devChatResponse.getContent();
        return content;
    }


}
