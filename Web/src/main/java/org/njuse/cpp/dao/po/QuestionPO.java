package org.njuse.cpp.dao.po;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

import javax.persistence.Column;

@Data
public class QuestionPO {
    private Long questionId;

    private String name;

    private String description;
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
