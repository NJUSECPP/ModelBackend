package org.njuse.cpp.dao.converter;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.po.QuestionPO;

public class QuestionConverter {
    public static QuestionBO po2bo(QuestionPO questionPO){
        QuestionBO questionBO=new QuestionBO();
        questionBO.setId(questionPO.getId());
        questionBO.setName(questionPO.getName());
        questionBO.setAnswer(questionPO.getAnswer());
        questionBO.setDescription(questionPO.getDescription());
        questionBO.setTestCase(questionPO.getTestCase());

        return questionBO;
    }
}
