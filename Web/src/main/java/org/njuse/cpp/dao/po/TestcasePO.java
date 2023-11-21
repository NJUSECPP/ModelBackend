package org.njuse.cpp.dao.po;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;


@Data
public class TestcasePO {
    private Long testId;
    private Long questionId;
    private String input;
    private String output;
    private String tip;
}
