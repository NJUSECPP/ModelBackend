package org.njuse.cpp.executor;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.llm.BaseLlm;
import org.njuse.cpp.llm.StarCoderLlm;
import org.njuse.cpp.memory.BaseChatMessageHistory;
import org.njuse.cpp.memory.BaseMessage;
import org.njuse.cpp.memory.HumanMessage;
import org.njuse.cpp.memory.SystemMessage;
import org.njuse.cpp.prompt.BasePromptTemplate;
import org.njuse.cpp.prompt.StarCoderPromptTemplate;
import org.njuse.cpp.tool.BaseTool;
import org.njuse.cpp.tool.OjRunTool;
import org.njuse.cpp.tool.enums.OjEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultExecutor extends BaseExecutor{
    private static final Map<String, BaseLlm> MODEL_POOL=new HashMap<String,BaseLlm>(){{
        put("StarCoder",new StarCoderLlm());
    }};

    private static final Map<String, BasePromptTemplate> PROMPT_TEMPLATE_MAP=new HashMap<String,BasePromptTemplate>()
    {{
        put("StarCoder",new StarCoderPromptTemplate());
    }};


    private static final Integer MAX_ROUND=3;


    @Override
    public void run(Map<String, Object> args) {
        BaseChatMessageHistory memory= (BaseChatMessageHistory) args.get("memory");
        QuestionBO questionBO= (QuestionBO) args.get("question");
        String modelName=(String)args.get("modelName");
        FluxSink<BaseMessage> emit=(FluxSink) args.get("emit");

        HumanMessage humanMessage=new HumanMessage("Description:"+questionBO.getDescription(),Collections.emptyMap());
        memory.addMessage(humanMessage);
        emit.next(humanMessage);

        BaseLlm model=MODEL_POOL.get(modelName);
        BasePromptTemplate promptTemplate=PROMPT_TEMPLATE_MAP.get(modelName);

        for(int i=0;i<MAX_ROUND;i++){
            String prompt=promptTemplate.parsePrompt(memory.loadMessages());
            Map<String,Object> llmResponse=model.call(prompt, Collections.emptyMap());

            OjRunTool ojRunTool=new OjRunTool();
            Map<String,Object> input=new HashMap<>();
            input.put("question",questionBO);
            input.put("code",llmResponse.get("code"));
            BaseMessage message= (BaseMessage) llmResponse.get("msg");
            emit.next(message);
            memory.addMessage(message);

            Map<String,Object> toolOutput=ojRunTool.run(input);
            OjEnum ojRes= (OjEnum) toolOutput.get("OjRes");
            if(ojRes.equals(OjEnum.PASS)){
                Map<String,Object> extendedParam=new HashMap<>();
                extendedParam.put("statusCode",0);
                SystemMessage systemMessage=new SystemMessage(ojRes.getMessage(), extendedParam);
                emit.next(systemMessage);
                emit.complete();
                break;
            }
            else{
                HumanMessage ojMessage=new HumanMessage(ojRes.getMessage(), Collections.emptyMap());
                emit.next(ojMessage);
                memory.addMessage(ojMessage);
            }
        }
        Map<String,Object> extendedParam=new HashMap<>();
        extendedParam.put("statusCode",1);
        SystemMessage systemMessage=new SystemMessage("The model cannot pass the oj",extendedParam);
        emit.next(systemMessage);
        emit.complete();
    }
}
