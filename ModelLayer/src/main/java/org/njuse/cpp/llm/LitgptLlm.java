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

public class LitgptLlm extends BaseLlm{
    private static final Logger logger=Logger.getLogger(LitgptLlm.class);
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
            res= HttpUtil.doPostRequest(client,"http://10.58.0.2:7904/output",json.toJSONString());
            logger.debug("LitgptLlm res:"+res);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        //调用模型返回的Map
        Map<String,Object> responseMap= JSON.parseObject(res,new TypeReference<Map<String,Object>>(){});
        String llmResponse= (String) responseMap.get("text");
        Integer startIndex=llmResponse.indexOf("#");
        Integer endIndex=llmResponse.indexOf("}");

        String codeGenerate=llmResponse.substring(startIndex,endIndex+1);
        logger.debug("LitgptLlm codeGenerate:"+codeGenerate);

        Map<String,Object> resMap=new HashMap<>();
        resMap.put("code",codeGenerate);
        AiMessage aiMessage=new AiMessage(codeGenerate, Collections.emptyMap());
        resMap.put("msg",aiMessage);

        return resMap;
    }
}
