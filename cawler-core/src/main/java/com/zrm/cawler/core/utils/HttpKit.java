package com.zrm.cawler.core.utils;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 张锐敏
 * @version V1.0
 * @description
 * @time 2015-06-01 10:42
 */
public class HttpKit {


    public static String get(String uri) throws IOException {
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");

        CloseableHttpResponse getResponse = httpclient.execute(httpGet);
        // The underlying HTTP connection is still held by the response object
        // to allow the response content to be streamed directly from the network socket.
        // In order to ensure correct deallocation of system resources
        // the user MUST call CloseableHttpResponse#close() from a finally clause.
        // Please note that if response content is not fully consumed the underlying
        // connection cannot be safely re-used and will be shut down and discarded
        // by the connection manager.
        try {
            StatusLine statusLine = getResponse.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                HttpEntity httpEntity = getResponse.getEntity();

                if (httpEntity != null){
                    result = EntityUtils.toString(httpEntity, Consts.UTF_8);
                }

                EntityUtils.consume(httpEntity);
                return result;
            }
        } finally {
            getResponse.close();
        }

        return result;
    }

    public static String post(String uri,Map<String,String> params) throws IOException {
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        if(params != null && params.size() > 0){
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for(Map.Entry<String, String> entry:params.entrySet()){
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        }

        CloseableHttpResponse postResponse = httpclient.execute(httpPost);

        try {
            StatusLine statusLine = postResponse.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                HttpEntity httpEntity = postResponse.getEntity();

                if (httpEntity != null){
                    result = EntityUtils.toString(httpEntity, Consts.UTF_8);
                }
                EntityUtils.consume(httpEntity);

                return result;
            }

        } finally {
            postResponse.close();
        }

        return result;
    }

    public static void main(String[] args){
        try {
            String url = "http://115.28.57.215:9080/pumblrApi/v1/picture/list/init";
            System.out.println(get(url));

            System.out.println(post(url,null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
