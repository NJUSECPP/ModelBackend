package org.njuse.cpp.dao.DTO;

import lombok.Data;

import java.util.List;

@Data
public class BatchInsertRequestDTO {
    private Integer questionId;
    private List<TestcaseRequestDTO> testcases;
}
