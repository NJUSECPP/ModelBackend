package org.njuse.cpp.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.TypeReference;
import okhttp3.*;
import org.apache.log4j.Logger;
import org.njuse.cpp.bo.QuestionBO;
import org.njuse.cpp.bo.TestResultBO;
import org.njuse.cpp.bo.TestcaseBO;
import org.njuse.cpp.tool.enums.OjEnum;
import org.njuse.cpp.util.HttpUtil;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OjRunTool extends BaseTool{
    private static final Logger logger= Logger.getLogger(OjRunTool.class);
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    //Oj接口返回code_result_status不为0时对应的错误
    private static final Map<Integer,OjEnum> ERROR_CODE_MAP=new HashMap<Integer,OjEnum>(){{
        put(-1,OjEnum.COMPILE_FAILED);
        put(1,OjEnum.CPU_TIME_LIMIT_EXCEEDED);
        put(2,OjEnum.REAL_TIME_LIMIT_EXCEEDED);
        put(3,OjEnum.MEMORY_LIMIT_EXCEEDED);
        put(4,OjEnum.RUNTIME_ERROR);
        put(5,OjEnum.SYSTEM_ERROR);
    }};


    private static OjEnum runAndCheck(String code, List<TestcaseBO> testcaseBOS){
        JSONObject json=new JSONObject();

        json.put("code","#include <bits/stdc++.h>\n"+code);
        json.put("testCases",testcaseBOS);
        String res;
        try {
            res= HttpUtil.doPostRequest(client,"http://172.29.4.19:8082/modelRacetrack/run", json.toJSONString());
            logger.debug("run res:"+res);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        Map<String,Object> resMap= JSON.parseObject(res,new TypeReference<Map<String,Object>>(){});

        Integer status= (Integer) resMap.get("code_result_status");
        if(ERROR_CODE_MAP.containsKey(status)){
            return ERROR_CODE_MAP.get(status);
        }
        else{
            JSONObject testResultJson=(JSONObject) resMap.get("test_result");
            TestResultBO testResultBO=JSON.parseObject(testResultJson.toJSONString(),TestResultBO.class);
            if(testResultBO.getStatus()==0){
                return OjEnum.PASS;
            }
            else{
                return OjEnum.INCORRECT_ANSWER;
            }
        }
    }


    @Override
    public Map<String, Object> run(Map<String, Object> args) {
        List<TestcaseBO> testcaseBO= (List<TestcaseBO>) args.get("testcases");
        String code= (String) args.get("code");
        OjEnum ojRes=runAndCheck(code,testcaseBO);

        logger.debug("ojRes:"+ojRes.name());
        Map<String,Object> resMap=new HashMap<>();
        resMap.put("OjRes",ojRes);
        return resMap;
    }
}
