package org.njuse.cpp.dao.po;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;


@Data
public class TestcasePO {
    private Long testid;
    private Long questionid;
    private String input;
    private String output;
    private String tip;
}
