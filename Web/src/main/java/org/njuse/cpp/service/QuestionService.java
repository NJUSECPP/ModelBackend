package org.njuse.cpp.service;

import org.njuse.cpp.bo.QuestionBO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    QuestionBO getQuestionById(Long questionId);


    List<QuestionBO> getQuestionsByPage(Integer pageSize, Integer pageNo, String keyword);

    int countQuestions(String keyword);

    QuestionBO addQuestion(String name, String description);

    QuestionBO updateQuestion(String id, String name, String description);

    void deleteQuestionAndTestcases(Long questionId);

}
