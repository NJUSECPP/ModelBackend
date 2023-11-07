package org.njuse.cpp.memory;

import java.util.Map;


public abstract class BaseMessage {
    private final String content;

    private final Map<String,Object> extendParams;

    public BaseMessage(String content,Map<String,Object> extendParams){
        this.content=content;
        this.extendParams=extendParams;
    }


    public abstract String type();

    public String getContent(){
        return this.content;
    }

    public Map<String,Object> getExtendParams(){
        return this.extendParams;
    }

    public Object getExtendParam(String key){
        return this.extendParams.get(key);
    }

}
