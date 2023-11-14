package org.njuse.cpp.util;

import okhttp3.*;

import java.io.IOException;

public class HttpUtil {


    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static String doPostRequest(OkHttpClient client,String url, String jsonBody) throws IOException {
        RequestBody body = RequestBody.create(JSON_TYPE,jsonBody);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
