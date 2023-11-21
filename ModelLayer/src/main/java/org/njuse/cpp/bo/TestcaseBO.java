package org.njuse.cpp.bo;

import lombok.Data;

@Data
public class TestcaseBO {
        private Long testId;
        private Long questionId;
        private String input;
        private String output;
        private String tip;
}
