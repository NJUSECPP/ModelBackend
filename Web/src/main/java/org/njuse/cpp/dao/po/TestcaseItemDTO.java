package org.njuse.cpp.dao.po;

import lombok.Data;

@Data
public class TestcaseItemDTO {
    private Long id;
    private String input;
    private String output;
    private String tip;
}
