package org.njuse.cpp.bo.entity;

import lombok.Data;

@Data
public class TestResult {
    Long testId;
    Long questionId;
    String input;
    String output;
    //用例运行结果 0正常 -1结果错误 -2超时 -3运行时异常
    Integer result;


}
