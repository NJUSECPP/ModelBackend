package org.njuse.cpp.bo;

import lombok.Data;

@Data
public class TestcaseBO {
        private Long testid;
        private Long questionid;
        private String input;
        private String output;
        private String tip;
}
