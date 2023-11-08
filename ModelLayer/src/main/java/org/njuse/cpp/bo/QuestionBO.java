package org.njuse.cpp.bo;

import lombok.Data;

@Data
public class QuestionBO {
    private Long id;
    private String name;
    private String description;
    private String testCase;
    private String answer;
}
