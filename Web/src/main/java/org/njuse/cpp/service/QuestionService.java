package org.njuse.cpp.service;

import org.njuse.cpp.bo.QuestionBO;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    QuestionBO getQuestionById(Integer questionId);
}
