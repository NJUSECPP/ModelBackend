package org.njuse.cpp.tool.enums;

public enum OjEnum {
    PASS("Your code is right and you've passed all the test cases."),
    INCORRECT_ANSWER("Your code cannot pass all the test cases.${extra_message}"),
    COMPILE_FAILED("Your code cannot be compiled successfully. Here is the compilation error information. " +
            "Error information: ${extra_message}. " +
            "Please correct the code according to the error information and give me a correct version of the code."),
    CPU_TIME_LIMIT_EXCEEDED("Your code has a CPU_TIME_LIMIT_EXCEEDED, " +
            "which means that your code did not produce a result within the specified time. " +
            "Please check the time complexity of your algorithm. " +
            "Modify and give me a correct version of the code."),
    REAL_TIME_LIMIT_EXCEEDED("Your code has a REAL_TIME_LIMIT_EXCEEDED, " +
            "which means that your code did not produce a result within the specified time. " +
            "Please check the time complexity of your algorithm. " +
            "You may also need to check if your program involves a large amount of I/O operations. " +
            "Modify and give me a correct version of the code."),
    MEMORY_LIMIT_EXCEEDED("Your code has a MEMORY_LIMIT_EXCEEDED, " +
            "which means that your code used more memory than specified in the problem description. " +
            "Please check the memory complexity of your algorithm. " +
            "Modify and give me a correct version of the code."),
    RUNTIME_ERROR("Your code has a RUNTIME_ERROR. " +
            "Please check if there are any possible errors such as array out-of-bounds, " +
            "stack overflow, or division by zero. " +
            "Please modify your code and give me a correct version of the code."),
    SYSTEM_ERROR("There is a SYSTEM_ERROR, I'll ask you later.");

    private String message;

    OjEnum(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
