package org.njuse.cpp.executor;

import org.njuse.cpp.memory.BaseChatMessageHistory;

import java.util.Map;

public abstract class BaseExecutor {
    BaseChatMessageHistory memory;


    public BaseExecutor(BaseChatMessageHistory memory){
        this.memory=memory;
    }


    abstract void run(Map<String,Object> extendParams);
}
