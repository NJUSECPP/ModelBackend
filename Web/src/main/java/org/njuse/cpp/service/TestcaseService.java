package org.njuse.cpp.service;
import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.dao.DTO.QuestionResponseDTO;
import org.njuse.cpp.dao.DTO.TestcaseRequestDTO;
import org.njuse.cpp.dao.po.QuestionPO;
import org.njuse.cpp.dao.po.TestcasePO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestcaseService {
    List<TestcasePO> getAllTestcases(Integer pageSize, Integer pageNo, Integer questionId);

    QuestionBO batchAddTestcases(Long questionId, List<TestcaseRequestDTO> testcases);

    Long batchDeleteTestcases(Integer[] testcaseIds);
}
