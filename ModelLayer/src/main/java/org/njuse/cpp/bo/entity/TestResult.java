package org.njuse.cpp.bo.entity;

import lombok.Data;

@Data
public class TestResult {
    Long testId;
    Long questionId;
    String input;

    //期待的输出
    String output;
    //实际输出
    String answer;
    //用例提示
    String tip;
    //用例运行结果 0正常 -1结果错误 -2超时 -3运行时异常
    Integer result;


}
