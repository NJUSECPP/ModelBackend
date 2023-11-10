package org.njuse.cpp.executor;

import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.llm.BaseLlm;
import org.njuse.cpp.memory.BaseChatMessageHistory;
import org.njuse.cpp.memory.BaseMessage;
<<<<<<< HEAD
=======
import reactor.core.publisher.Flux;
>>>>>>> ec42ab482970bc4e6c62fc32d8ecde478962f6c2
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.Map;

public class DefaultExecutor extends BaseExecutor{
    private static final Map<String, BaseLlm> MODEL_POOL=new HashMap<String,BaseLlm>(){{

    }};


    @Override
    public void run(Map<String, Object> extendParams) {
        BaseChatMessageHistory memory= (BaseChatMessageHistory) extendParams.get("memory");
        QuestionBO questionBO= (QuestionBO) extendParams.get("question");
        String modelName=(String)extendParams.get("modelName");
        FluxSink<BaseMessage> emit=(FluxSink) extendParams.get("emit");


    }
}
