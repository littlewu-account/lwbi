package com.wjy.lwbi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@SpringBootTest
public class WriteUserInputTest {

    @Test
    public void testUserInput() {
        String topic = "值得骄傲的中国队";
        String keyWord = "金牌榜榜首，运动健儿，青春";
        String articleScene = "亚运会心得";
        String articleType = "报告";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("请以");
        stringBuilder.append("\"");
        stringBuilder.append(topic);
        stringBuilder.append("\"");
        stringBuilder.append("为主题，根据");
        stringBuilder.append("\"");
        stringBuilder.append(keyWord);
        stringBuilder.append("\"");
        stringBuilder.append("这些关键字，");
        stringBuilder.append("攥写一篇关于");
        stringBuilder.append(articleScene);
        stringBuilder.append("的");
        stringBuilder.append(articleType);
        String result = stringBuilder.toString();
        System.out.println(result);
    }

}
