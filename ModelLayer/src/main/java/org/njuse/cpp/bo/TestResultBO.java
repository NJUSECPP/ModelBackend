package org.njuse.cpp.bo;

import lombok.Data;
import org.njuse.cpp.bo.entity.TestResult;

import java.util.List;

@Data
public class TestResultBO {
    Integer status;
    List<TestResult> testResultList;

}
