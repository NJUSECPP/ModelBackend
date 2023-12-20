package org.njuse.cpp.prompt;

import org.apache.log4j.Logger;
import org.njuse.cpp.llm.Llama7bLlm;
import org.njuse.cpp.memory.BaseMessage;

import java.util.List;

public class LitgptPromptTemplate extends BasePromptTemplate{
    private static final Logger logger=Logger.getLogger(LitgptPromptTemplate.class);

    private static final String INS_BEGIN="[INST]";

    private static final String INS_END="[/INST]";

    private static final String ESCAPE_BEGIN="<s>";

    private static final String ESCAPE_END="</s>";


    @Override
    public String parsePrompt(List<BaseMessage> messages) {
        StringBuffer promptBuffer=new StringBuffer();
        if(messages.isEmpty()){
            throw new RuntimeException("Message List Cannot be empty");
        }
        for(int i=0;i<messages.size()-1;i++){
            if(i%2==0){
                promptBuffer.append(ESCAPE_BEGIN)
                        .append(INS_BEGIN)
                        .append(messages.get(i).getContent())
                        .append(INS_END)
                        .append(messages.get(i+1).getContent())
                        .append(ESCAPE_END);
            }
            else{
                continue;
            }
        }
        promptBuffer.append(ESCAPE_BEGIN)
                .append(INS_BEGIN)
                .append(messages.get(messages.size()-1).getContent())
                .append(INS_END);
        logger.debug("Litgpt prompt:"+promptBuffer.toString());

        return promptBuffer.toString();
    }
}
