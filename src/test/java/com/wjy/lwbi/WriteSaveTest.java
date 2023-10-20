package com.wjy.lwbi;

import com.wjy.lwbi.model.entity.Write;
import com.wjy.lwbi.service.WriteService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@SpringBootTest
public class WriteSaveTest {

    @Resource
    private WriteService writeService;

    @Test
    public void testWriteSave() {
        String topic = "值得骄傲的中国队";
        String keyWord = "金牌榜榜首，运动健儿，青春";
        String articleScene = "亚运会心得";
        String articleType = "报告";
        Write write = new Write();
        write.setTopic(topic);
        write.setArticleScene(articleScene);
        write.setArticleType(articleType);
        write.setGenResult("奥运加油！");
        write.setKeyWord(keyWord);
        write.setName("报告123456");
        write.setStatus("成功");
        write.setUserId(1L);
        writeService.save(write);
    }

}
