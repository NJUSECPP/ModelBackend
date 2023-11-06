package org.njuse.cpp.prompt;

import org.njuse.cpp.memory.BaseMessage;

import java.util.List;

public class StarCoderPromptTemplate extends BasePromptTemplate{
    private static final String SYS_TOKEN="<|system|>";
    private static final String USER_TOKEN="<|user|>";
    private static final String ASSISTANT_TOKEN="<|assistant|>";
    private static final String END_TOKEN="<|end|>";


    @Override
    String parsePrompt(List<BaseMessage> messages) {

        return null;
    }
}
