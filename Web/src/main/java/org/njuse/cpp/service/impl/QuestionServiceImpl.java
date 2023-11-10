package org.njuse.cpp.service.impl;

import org.njuse.cpp.bo.QuestionBO;
<<<<<<< HEAD
import org.njuse.cpp.dao.QuestionMapper;
import org.njuse.cpp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class QuestionServiceImpl implements QuestionService {
    //TODO: 实现根据id获取QuestionBO

    private QuestionMapper questionMapper;

    @Autowired
    public QuestionServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public QuestionBO getQuestionById(Integer questionId){
        Long id=Long.valueOf(questionId);
        return questionMapper.getQuestionById(id);
    }



=======
import org.njuse.cpp.service.QuestionService;

public class QuestionServiceImpl implements QuestionService {
    //TODO: 实现根据id获取QuestionBO
    @Override
    public QuestionBO getQuestionById(Integer questionId) {
        return null;
    }


>>>>>>> ec42ab482970bc4e6c62fc32d8ecde478962f6c2
}
