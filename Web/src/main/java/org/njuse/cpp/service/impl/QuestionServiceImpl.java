package org.njuse.cpp.service.impl;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.QuestionMapper;
import org.njuse.cpp.dao.converter.QuestionConverter;
import org.njuse.cpp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
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


        return QuestionConverter.po2bo(questionMapper.getQuestionById(id));
    }
}




