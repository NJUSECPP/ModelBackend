package org.njuse.cpp.memory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;


public class HumanMessage extends BaseMessage {
    public HumanMessage(String content, Map<String,Object> extendParams){
        super(content,extendParams);
    }
    @Override
    public String type() {
        return "human";
    }
}
