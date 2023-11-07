package org.njuse.cpp.memory;

import java.util.Map;

public class SystemMessage extends BaseMessage{
    public SystemMessage(String content, Map<String, Object> extendParams) {
        super(content, extendParams);
    }


    @Override
    public String type() {
        return "system";
    }
}
