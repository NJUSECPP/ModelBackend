package org.njuse.cpp.executor;

import org.njuse.cpp.memory.BaseChatMessageHistory;
import reactor.core.publisher.Flux;

import java.util.Map;

public abstract class BaseExecutor {

    public abstract void run(Map<String,Object> extendParams);
}
