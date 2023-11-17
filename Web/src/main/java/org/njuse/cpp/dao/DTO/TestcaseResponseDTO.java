package org.njuse.cpp.dao.DTO;

import lombok.Data;
import org.njuse.cpp.dao.converter.TestcaseConverter;
import org.njuse.cpp.dao.po.TestcaseItemDTO;
import org.njuse.cpp.dao.po.TestcasePO;

import java.util.List;

@Data
public class TestcaseResponseDTO {
    private TestcaseItemDTO[] testcases;
    private int total;
    public static TestcaseResponseDTO fromListAndTotal(List<TestcasePO> poList, int total) {
        TestcaseResponseDTO responseDTO = new TestcaseResponseDTO();
        responseDTO.setTestcases(TestcaseConverter.poListToItemDTOArray(poList));
        responseDTO.setTotal(total);
        return responseDTO;
    }
}
