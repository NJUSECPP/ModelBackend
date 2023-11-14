package org.njuse.cpp.tool.enums;

public enum OjEnum {
    PASS("The code is Right"),INCORRECT_ANSWER("The answer is incorrect"),COMPILE_FAILED(""),CPU_TIME_LIMIT_EXCEEDED(""),
    REAL_TIME_LIMIT_EXCEEDED(""),MEMORY_LIMIT_EXCEEDED(""),RUNTIME_ERROR(""),SYSTEM_ERROR("");

    //TODO:: 根据Oj 结果给模型一些反馈
    private String message;

    OjEnum(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
