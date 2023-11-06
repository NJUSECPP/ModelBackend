package org.njuse.cpp.memory;

import java.util.List;

public class SqlMemory extends BaseChatMessageHistory{
    private String sessionId;


    @Override
    void clear() {

    }

    @Override
    List<BaseMessage> loadMessages() {
        return null;
    }

    @Override
    void addMessage(BaseMessage message) {

    }
}
