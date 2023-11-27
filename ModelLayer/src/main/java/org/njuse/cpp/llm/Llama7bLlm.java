package org.njuse.cpp.llm;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import okhttp3.OkHttpClient;
import org.apache.log4j.Logger;
import org.njuse.cpp.memory.AiMessage;
import org.njuse.cpp.util.HttpUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.text.StringEscapeUtils;


public class Llama7bLlm extends BaseLlm{
    private static final Logger logger=Logger.getLogger(Llama7bLlm.class);
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build();
    @Override
    public Map<String, Object> call(String prompt, Map<String, Object> extendedParams) {
        JSONArray promptArray = new JSONArray();
        JSONObject promptObject = new JSONObject();
        promptObject.put("role", "user");
        promptObject.put("content", prompt);
        promptArray.add(promptObject);

        JSONObject json = new JSONObject();
        json.put("prompt", JSON.toJSONString(promptArray));

        String jsonString = JSON.toJSONString(json);
        String res;
        try {
            res=HttpUtil.doPostRequest(client,"http://10.58.0.2:6904/output",jsonString);
            logger.debug(jsonString);
            logger.debug("llama7b res:"+res);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }

        Map<String,Object> responseMap= JSON.parseObject(res,new TypeReference<Map<String,Object>>(){});
        String llmResponse= (String) responseMap.get("text");
        String codeGenerate;
        if(llmResponse.contains("```")){
            codeGenerate=llmResponse.split("```")[1];
        }else {
            codeGenerate=getMessageContent(llmResponse);
        }
        logger.debug("llama7b codeGenerate:"+codeGenerate);

        Map<String,Object> resMap=new HashMap<>();
        resMap.put("code",codeGenerate);
        String msgContent=getMessageContent(llmResponse);
        AiMessage aiMessage=new AiMessage(msgContent, Collections.emptyMap());
        resMap.put("msg",aiMessage);
        return resMap;

    }

    private static String getMessageContent(String text){
        return text;
    }
}

