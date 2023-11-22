package org.njuse.cpp.dao.DTO;

import lombok.Data;

import java.util.List;

@Data
public class BatchInsertRequestDTO {
    private Long questionId;
    private List<TestcaseRequestDTO> testcases;
}
