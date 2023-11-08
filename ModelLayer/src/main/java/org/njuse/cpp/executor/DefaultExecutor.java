package org.njuse.cpp.executor;

import org.njuse.cpp.memory.BaseChatMessageHistory;
import reactor.core.publisher.Flux;

import java.util.Map;

public class DefaultExecutor extends BaseExecutor{


    @Override
    public void run(Map<String, Object> extendParams) {
        BaseChatMessageHistory memory= (BaseChatMessageHistory) extendParams.getOrDefault("memory",null);


    }
}
