package org.njuse.cpp.request;

import lombok.Data;

@Data
public class GenerateRequest {

    private Integer modelId;
    private String  questionId;
    private Integer userId ;
    private String  userName;
    private String  sessionId;
    private String  question;

}
