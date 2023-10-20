package com.wjy.lwbi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wjy.lwbi.common.ErrorCode;
import com.wjy.lwbi.constant.CommonConstant;
import com.wjy.lwbi.exception.BusinessException;
import com.wjy.lwbi.manager.RedissonManager;
import com.wjy.lwbi.manager.WriteAiManager;
import com.wjy.lwbi.mapper.WriteMapper;
import com.wjy.lwbi.model.dto.write.GenWriteByAiRequest;
import com.wjy.lwbi.model.entity.User;
import com.wjy.lwbi.model.entity.Write;
import com.wjy.lwbi.model.vo.WriteResponse;
import com.wjy.lwbi.service.UserService;
import com.wjy.lwbi.service.WriteService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author littlew
* @description 针对表【write(创作信息表)】的数据库操作Service实现
* @createDate 2023-10-19 20:10:34
*/
@Service
public class WriteServiceImpl extends ServiceImpl<WriteMapper, Write>
    implements WriteService {

    @Resource
    private WriteAiManager writeAiManager;

    @Resource
    private UserService userService;

    @Resource
    private RedissonManager redissonManager;

    @Override
    public WriteResponse genWriteByAi(GenWriteByAiRequest genWriteByAiRequest, HttpServletRequest request) {

        //用户限流
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        String limitKey = "genWriteByAi" + userId;
        redissonManager.doRateLimit(limitKey);

        //拼接用户的输入
        String userInput = userInput(genWriteByAiRequest);

        //调用Ai进行写作
        String content = writeAiManager.doChat(CommonConstant.WRITE_MODEL_ID, userInput);
        WriteResponse writeResponse = new WriteResponse();

        //封装成Write对象，存数据库
        Write write = buildWrite(genWriteByAiRequest, content, userId);
        boolean saveResult = this.save(write);
        if(!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR.getCode(), "报告信息保存失败");
        }
        writeResponse.setGenResult(content);
        writeResponse.setWriteId(write.getId());
        return writeResponse;
    }

    private String userInput(GenWriteByAiRequest genWriteByAiRequest) {
        String topic = genWriteByAiRequest.getTopic();
        String keyWord = genWriteByAiRequest.getKeyWord();
        String articleScene = genWriteByAiRequest.getArticleScene();
        String articleType = genWriteByAiRequest.getArticleType();
        if (!StringUtils.hasText(topic) || !StringUtils.hasText(keyWord) || !StringUtils.hasText(articleScene) || !StringUtils.hasText(articleType)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求数据异常");
        }

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
        return result;
    }

    private Write buildWrite(GenWriteByAiRequest genWriteByAiRequest, String content, Long userId) {
        Write write = new Write();
        write.setTopic(genWriteByAiRequest.getTopic());
        write.setArticleScene(genWriteByAiRequest.getArticleScene());
        write.setArticleType(genWriteByAiRequest.getArticleType());
        write.setGenResult(content);
        write.setKeyWord(genWriteByAiRequest.getKeyWord());
        write.setName(genWriteByAiRequest.getName());
        write.setStatus("成功");
        write.setUserId(userId);
        return write;
    }
}




