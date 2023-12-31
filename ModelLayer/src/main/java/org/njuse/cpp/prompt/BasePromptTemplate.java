package org.njuse.cpp.prompt;

import org.njuse.cpp.memory.BaseMessage;

import java.util.List;

public abstract class BasePromptTemplate {
    public abstract String parsePrompt(List<BaseMessage> messages);
}
