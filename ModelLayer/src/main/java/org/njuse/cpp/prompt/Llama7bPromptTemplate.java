package org.njuse.cpp.prompt;

import org.apache.commons.lang3.StringUtils;
import org.njuse.cpp.memory.BaseMessage;

import java.util.List;

public class Llama7bPromptTemplate extends BasePromptTemplate{
    public static final String SYS_BEGIN_TOKEN="Here is SystemMessage";
    public static final String SYS_END_TOKEN="SystemMessage End";
    public static final String USER_BEGIN_TOKEN="Here is UserMessage";
    public static final String USER_END_TOKEN="UserMessage End";
    public static final String ASSISTANT_BEGIN_TOKEN="Here is AssistantMessage";
    public static final String ASSISTANT_END_TOKEN="UserMessage End";

    public static final String SYS_MSG="";



    @Override
    public String parsePrompt(List<BaseMessage> messages) {
        StringBuffer promptBuffer=new StringBuffer();
        if(StringUtils.isNotBlank(SYS_MSG)){
            promptBuffer.append(SYS_BEGIN_TOKEN+"\n" + SYS_MSG + SYS_END_TOKEN + "\n");
        }
        if(messages.isEmpty()){
            throw new RuntimeException("Message List Cannot be empty");
        }
        for(BaseMessage message:messages){
            if(message.type().equals("ai")){
                promptBuffer.append(ASSISTANT_BEGIN_TOKEN+"\n" + message.getContent() + ASSISTANT_END_TOKEN + "\n");
            }
            else if(message.type().equals("human")){
                promptBuffer.append(USER_BEGIN_TOKEN + "\n" + message.getContent() + USER_END_TOKEN + "\n");
            }
        }

        return promptBuffer.toString();
    }
}
