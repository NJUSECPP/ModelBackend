package org.njuse.cpp.dao.converter;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.bo.TestcaseBO;
import org.njuse.cpp.dao.DTO.TestcaseRequestDTO;
import org.njuse.cpp.dao.po.QuestionPO;
import org.njuse.cpp.dao.po.TestcaseItemDTO;
import org.njuse.cpp.dao.po.TestcasePO;

import java.util.ArrayList;
import java.util.List;

public class TestcaseConverter {
    public static TestcaseBO po2bo(TestcasePO testcasepo){
        TestcaseBO testcaseBO=new TestcaseBO();
        testcaseBO.setInput(testcasepo.getInput());
        testcaseBO.setOutput(testcasepo.getOutput());
        testcaseBO.setTestId(testcasepo.getTestId());
        testcaseBO.setQuestionId(testcasepo.getQuestionId());
        testcaseBO.setTip(testcasepo.getTip());
        return testcaseBO;
    }
    public static TestcaseItemDTO poToItemDTO(TestcasePO po) {
        TestcaseItemDTO dto = new TestcaseItemDTO();
        dto.setId(po.getTestId());
        dto.setInput(po.getInput());
        dto.setOutput(po.getOutput());
        dto.setTip(po.getTip());
        return dto;
    }
    public static TestcaseItemDTO[] poListToItemDTOArray(List<TestcasePO> poList) {
        TestcaseItemDTO[] dtoArray = new TestcaseItemDTO[poList.size()];
        for (int i = 0; i < poList.size(); i++) {
            dtoArray[i] = poToItemDTO(poList.get(i));
        }
        return dtoArray;
    }

    public static List<TestcasePO> dtoList2poList(Long questionId, List<TestcaseRequestDTO> testcaseDTOList) {
        List<TestcasePO> testcasePOList = new ArrayList<>();
        for (TestcaseRequestDTO testcaseDTO : testcaseDTOList) {
            TestcasePO testcasePO = new TestcasePO();
            testcasePO.setQuestionId(questionId);
            testcasePO.setInput(testcaseDTO.getInput());
            testcasePO.setOutput(testcaseDTO.getOutput());
            testcasePO.setTip(testcaseDTO.getTip());
            // 其他设置...
            testcasePOList.add(testcasePO);
        }
        return testcasePOList;
    }
}
