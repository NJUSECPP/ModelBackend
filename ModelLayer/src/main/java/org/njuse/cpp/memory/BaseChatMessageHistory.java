package org.njuse.cpp.memory;

import java.util.List;

public abstract class BaseChatMessageHistory {
    void addUserMessage(String content){
        HumanMessage humanMessage=new HumanMessage(content,null);
        this.addMessage(humanMessage);
    }

    void addAiMessage(String content){
        AiMessage aiMessage=new AiMessage(content,null);
        this.addMessage(aiMessage);
    }

    public abstract void clear();

    public abstract List<BaseMessage> loadMessages();

    public abstract void addMessage(BaseMessage message);
}
