package com.wjy.lwbi.model.dto.write;

import lombok.Data;

/**
 * @author 吴峻阳
 * @version 1.0
 */
@Data
public class GenWriteByAiRequest {

    private String topic;

    private String keyWord;

    private String articleScene;

    private String articleType;

    private String name;

}
