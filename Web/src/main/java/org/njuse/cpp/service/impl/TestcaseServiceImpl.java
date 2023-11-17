package org.njuse.cpp.service.impl;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.DTO.TestcaseRequestDTO;
import org.njuse.cpp.dao.QuestionMapper;
import org.njuse.cpp.dao.TestcaseMapper;
import org.njuse.cpp.dao.converter.QuestionConverter;
import org.njuse.cpp.dao.converter.TestcaseConverter;
import org.njuse.cpp.dao.po.QuestionPO;
import org.njuse.cpp.dao.po.TestcasePO;
import org.njuse.cpp.service.TestcaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestcaseServiceImpl implements TestcaseService {
    @Autowired
    TestcaseMapper testcaseMapper;

    private TestcaseConverter testcaseConverter;
    @Autowired
    QuestionMapper questionMapper;
    @Override
    public List<TestcasePO> getAllTestcases(Integer pageSize, Integer pageNo, Integer questionId) {

        if (pageSize == null || pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        int offset = (pageNo - 1) * pageSize;

        return testcaseMapper.getTestcasesByQuestionIdAndPage(pageSize, offset, questionId.longValue());
    }

    @Override
    public QuestionBO batchAddTestcases(Long questionId, List<TestcaseRequestDTO> testcases) {
        List<TestcasePO> testcasePOList = testcaseConverter.dtoList2poList(questionId, testcases);
        testcaseMapper.batchInsertTestcases(questionId,testcasePOList);
        QuestionPO questionPO = questionMapper.getQuestionById(questionId);
        return QuestionConverter.po2bo(questionPO);
    }

    @Override
    public Long batchDeleteTestcases(Integer[] testcaseIds) {
        TestcasePO testcasePO = testcaseMapper.getTestcaseById(testcaseIds[0].longValue());
        testcaseMapper.batchDeleteTestcases(testcaseIds);
        if (testcasePO != null && testcasePO.getQuestionid() != null) {
            Long questionId = testcasePO.getQuestionid();
            return  questionId;
        } else {
            return null; // 没有测试用例被删除，返回null或根据实际情况修改
        }

    }

}
