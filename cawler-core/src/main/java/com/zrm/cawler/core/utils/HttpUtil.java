package com.zrm.cawler.core.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpUtil {

    public static String get(String url) throws IOException {

        String responseBody = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity, "utf-8") : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            responseBody = httpclient.execute(httpget, responseHandler);
        } finally {
            httpclient.close();
        }
        return responseBody;
    }

    public static String post(String url, String json) throws IOException {
        StringBuilder result = new StringBuilder();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
            StringEntity se = new StringEntity(json);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            HttpResponse httpResponse = httpclient.execute(post);
            int httpCode = httpResponse.getStatusLine().getStatusCode();
            if (httpCode == HttpURLConnection.HTTP_OK && httpResponse != null) {
                HttpEntity entity = httpResponse.getEntity();
                //读取服务器返回的json数据（接受json服务器数据）
                InputStream inputStream = entity.getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
                String s;
                while (((s = reader.readLine()) != null)) {
                    result.append(s);
                }
                reader.close();// 关闭输入流
            }
        } finally {
            httpclient.close();
        }

        return result.toString();
    }

    public static void main(String args[]) throws IOException {
//      String url = "http://localhost/rest/wx/token";
//      System.out.println("response=" + get(url));
    }
}
