package org.njuse.cpp.request;

import lombok.Data;

@Data
public class GenerateRequest {
    private String modelName;
    private Integer  questionId;
    private Integer userId ;
    private String  userName;
    private String  sessionId;

}
