package org.njuse.cpp.service;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.DTO.QuestionResponseDTO;
import org.njuse.cpp.dao.po.QuestionPO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    QuestionBO getQuestionById(Integer questionId);


    List<QuestionBO> getQuestionsByPage(Integer pageSize, Integer pageNo, String keyword);

    int countQuestions(String keyword);

    QuestionBO addQuestion(String name, String description);

    QuestionBO updateQuestion(String id, String name, String description);

    void deleteQuestionAndTestcases(Long questionId);

}
