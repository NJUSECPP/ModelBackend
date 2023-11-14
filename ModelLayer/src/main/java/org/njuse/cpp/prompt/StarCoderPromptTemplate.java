package org.njuse.cpp.prompt;

import org.apache.commons.lang3.StringUtils;
import org.njuse.cpp.memory.BaseMessage;

import java.util.List;

public class StarCoderPromptTemplate extends BasePromptTemplate{
    public static final String SYS_TOKEN="<|system|>";
    public static final String USER_TOKEN="<|user|>";
    public static final String ASSISTANT_TOKEN="<|assistant|>";
    public static final String END_TOKEN="<|end|>";

    public static final String SYS_MSG="";


    @Override
    public String parsePrompt(List<BaseMessage> messages) {
        StringBuffer promptBuffer=new StringBuffer();
        if(StringUtils.isNotBlank(SYS_MSG)){
            promptBuffer.append(SYS_TOKEN+"\n" + SYS_MSG + END_TOKEN + "\n");
        }
        if(messages.isEmpty()){
            throw new RuntimeException("Message List Cannot be empty");
        }
        for(BaseMessage message:messages){
            if(message.type().equals("ai")){
                promptBuffer.append(ASSISTANT_TOKEN+"\n" + message.getContent() + END_TOKEN + "\n");
            }
            else if(message.type().equals("human")){
                promptBuffer.append(USER_TOKEN + "\n" + message.getContent() + END_TOKEN + "\n");
            }
        }
        promptBuffer.append(ASSISTANT_TOKEN);


        return promptBuffer.toString();
    }
}
