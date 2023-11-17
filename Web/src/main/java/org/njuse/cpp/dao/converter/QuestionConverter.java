package org.njuse.cpp.dao.converter;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.po.QuestionPO;

public class QuestionConverter {
    public static QuestionBO po2bo(QuestionPO questionPO){
        QuestionBO questionBO=new QuestionBO();
        questionBO.setName(questionPO.getName());
        questionBO.setDescription(questionPO.getDescription());
        questionBO.setQuestionId(questionPO.getQuestionId());
        return questionBO;
    }
}
