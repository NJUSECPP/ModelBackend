package org.njuse.cpp.prompt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.njuse.cpp.memory.BaseMessage;

import java.util.List;

public class Gpt4PromptTemplate extends BasePromptTemplate{
    private static final Logger logger=Logger.getLogger(Gpt4PromptTemplate.class);

    @Override
    public String parsePrompt(List<BaseMessage> messages) {
        JSONArray jsonArray=new JSONArray();
        for(BaseMessage message:messages){
            JSONObject msgObject=new JSONObject();
            if(message.type().equals("ai")){
                msgObject.put("role","assistant");
            } else if (message.type().equals("human")){
                msgObject.put("role","user");
            } else if (message.type().equals("system")) {
                msgObject.put("role","system");
            }
            msgObject.put("content",message.getContent());
            jsonArray.add(msgObject);
        }
        String prompt=jsonArray.toJSONString();
        logger.debug("Gpt4 prompt:"+prompt);
        return prompt;
    }
}
