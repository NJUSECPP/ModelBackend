package org.njuse.cpp.dao.DTO;

import lombok.Data;
import org.njuse.cpp.bo.QuestionBO;

import java.util.List;

@Data
public class QuestionResponseDTO {
    private List<QuestionBO> questions;
    private int total;
    public QuestionResponseDTO(List<QuestionBO> questions, int total) {
        this.questions = questions;
        this.total = total;
    }
}

