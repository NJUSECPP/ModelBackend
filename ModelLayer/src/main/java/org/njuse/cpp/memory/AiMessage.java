package org.njuse.cpp.memory;

import java.util.Map;

public class AiMessage extends BaseMessage{
    public AiMessage(String content, Map<String, Object> extendParams) {
        super(content, extendParams);
    }

    @Override
    String type() {
        return "ai";
    }
}
