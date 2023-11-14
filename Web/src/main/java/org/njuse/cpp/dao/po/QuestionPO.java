package org.njuse.cpp.dao.po;

import lombok.Data;

import javax.persistence.Column;

@Data
public class QuestionPO {
    private Long id;

    private String name;

    private String description;

    private String testCase;

    private String answer;
}
