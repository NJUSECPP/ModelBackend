package org.njuse.cpp.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import okhttp3.*;

import java.io.IOException;

public class OJTest {
    private static Logger logger= LoggerFactory.getLogger(OJTest.class);
    private static final OkHttpClient client = new OkHttpClient();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String testCode="#include <iostream>\nusing namespace std;\n \nint main() {\n    int a, b;\n    cin >> a >> b;\n    cout << a + b;\n    return 0;\n}\n";
    public static void invoke() throws InterruptedException {
        login();
        Thread.sleep(3000l);
        runAndCheck("","","");

    }

    private static void login(){

        try {
            String res=doPostRequest("http://172.29.4.19:8082/web/login?username=gpt_test_user&password=123456","");
            System.out.println("login res:"+res);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static String runAndCheck(String code,String testCase,String answer){
        JSONObject json=new JSONObject();
        json.put("examId",10032);
        json.put("questionId",10079);
        json.put("username","gpt_test_user");
        json.put("code","#include <bits/stdc++.h>\n"+testCode);
        String res;
        try {
            res=doPostRequest("http://172.29.4.19:8082/test/self", json.toJSONString());
            System.out.println("run res:"+res);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return res;

    }



    private static String doPostRequest(String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(JSON,jsonBody);
        System.out.println(jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
