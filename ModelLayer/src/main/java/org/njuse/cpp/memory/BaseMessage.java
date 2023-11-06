package org.njuse.cpp.memory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;


public abstract class BaseMessage {
    String content;

    Map<String,Object> extendParams;

    public BaseMessage(String content,Map<String,Object> extendParams){
        this.content=content;
        this.extendParams=extendParams;
    }


    abstract String type();

}
