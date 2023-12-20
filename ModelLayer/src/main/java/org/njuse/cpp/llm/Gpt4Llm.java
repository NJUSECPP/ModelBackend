package org.njuse.cpp.llm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import okhttp3.OkHttpClient;
import org.apache.log4j.Logger;
import org.njuse.cpp.memory.AiMessage;
import org.njuse.cpp.util.HttpUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Gpt4Llm extends BaseLlm{
    private static final Logger logger=Logger.getLogger(Gpt4Llm.class);
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build();
    @Override
    public Map<String, Object> call(String prompt, Map<String, Object> extendedParams) {
        JSONObject json=new JSONObject();
        json.put("prompt",prompt);

        String res;
        try {
            res= HttpUtil.doPostRequest(client,"http://172.19.185.1:7904/output",json.toJSONString());
            logger.debug("Gpt4 res:"+res);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        Map<String,Object> responseMap= JSON.parseObject(res,new TypeReference<Map<String,Object>>(){});
        String llmResponse= (String) responseMap.get("text");
        Integer startIndex=llmResponse.indexOf("#");
        Integer endIndex=llmResponse.lastIndexOf("}");

        String codeGenerate=llmResponse.substring(startIndex,endIndex+1);
        logger.debug("Gpt4 codeGenerate:"+codeGenerate);

        Map<String,Object> resMap=new HashMap<>();
        resMap.put("code",codeGenerate);
        AiMessage aiMessage=new AiMessage(codeGenerate, Collections.emptyMap());
        resMap.put("msg",aiMessage);

        return resMap;
    }
}
