package org.njuse.cpp.executor;

import org.njuse.cpp.memory.BaseChatMessageHistory;

import java.util.Map;

public class DefaultExecutor extends BaseExecutor{

    public DefaultExecutor(BaseChatMessageHistory memory) {
        super(memory);
    }

    @Override
    void run(Map<String, Object> extendParams) {
        
    }
}
