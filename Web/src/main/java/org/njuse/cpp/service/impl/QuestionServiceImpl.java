package org.njuse.cpp.service.impl;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.DTO.QuestionResponseDTO;
import org.njuse.cpp.dao.QuestionMapper;
import org.njuse.cpp.dao.TestcaseMapper;
import org.njuse.cpp.dao.converter.QuestionConverter;
import org.njuse.cpp.dao.po.QuestionPO;
import org.njuse.cpp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionServiceImpl implements QuestionService {
    //TODO: 实现根据id获取QuestionBO

    private QuestionMapper questionMapper;
    @Autowired
    TestcaseMapper testcaseMapper;

    @Autowired
    public QuestionServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public QuestionBO getQuestionById(Integer questionId){
        Long id=Long.valueOf(questionId);


        return QuestionConverter.po2bo(questionMapper.getQuestionById(id));
    }

    @Override
    public List<QuestionBO> getQuestionsByPage(Integer pageSize, Integer pageNo, String keyword) {//分页查询
        List<QuestionPO> questionPOList = questionMapper.getQuestionsByPage(pageSize, pageNo, keyword);
        return convertPOListToBOList(questionPOList);
    }

    @Override
    public int countQuestions(String keyword) {//分页查询获取符合条件的总数
        return questionMapper.countQuestions(keyword);
    }

    @Override
    public QuestionBO addQuestion(String name, String description) {
        QuestionPO questionPO = new QuestionPO();
        questionPO.setName(name);
        questionPO.setDescription(description);

        // 插入数据库
        questionMapper.insertQuestion(questionPO);
        Long questionId = questionPO.getQuestionId();

        // 转换 QuestionPO 为 QuestionBO
        QuestionBO questionBO = QuestionConverter.po2bo(questionPO);

        return questionBO;
    }

    @Override
    public QuestionBO updateQuestion(String id, String name, String description) {
        Long questionId = Long.parseLong(id);

        // 创建或检索 QuestionPO 对象
        QuestionPO questionPO = questionMapper.getQuestionById(questionId);
        if (questionPO == null) {
            // 处理题目不存在的情况
            return null; // 或抛出异常
        }

        // 更新 QuestionPO 的属性
        questionPO.setName(name);
        questionPO.setDescription(description);
        questionPO.setQuestionId(questionId);
        // 更新数据库中的记录
        questionMapper.updateQuestion(questionPO);

        // 转换 QuestionPO 为 QuestionBO
        QuestionBO questionBO = QuestionConverter.po2bo(questionPO);

        return questionBO;
    }

    @Override
    public void deleteQuestionAndTestcases(Long questionId) {
        questionMapper.deleteQuestion(questionId);

        // 2. 删除该题目的所有用例
        testcaseMapper.deleteTestcasesByQuestionId(questionId);
    }


    private List<QuestionBO> convertPOListToBOList(List<QuestionPO> questionPOList) {
        List<QuestionBO> questionBOList = new ArrayList<>();
        for (QuestionPO questionPO : questionPOList) {
            questionBOList.add(QuestionConverter.po2bo(questionPO));
        }
        return questionBOList;
    }

}




