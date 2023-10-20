package com.wjy.lwbi;

import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@SpringBootTest
public class YupiClientTest {

    @Test
    public void testYuPiClient() {
        String accessKey = "csgdmst17hayfkxw1w9entn9u9rops8x";
        String secretKey = "9xngbfbkmtia2rkobcjtfjzonh5v9cwx";
        YuCongMingClient yuCongMingClient = new YuCongMingClient(accessKey, secretKey);
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1714972799262937089L);
        devChatRequest.setMessage("请以骄傲的中国队为主题，攥写一篇亚运会心得报告");
        BaseResponse<DevChatResponse> devChatResponseBaseResponse = yuCongMingClient.doChat(devChatRequest);
        System.out.println(devChatResponseBaseResponse);
        DevChatResponse data = (DevChatResponse)devChatResponseBaseResponse.getData();
        if (data != null) {
            String content = data.getContent();
            System.out.println(content);
        }
    }

}
