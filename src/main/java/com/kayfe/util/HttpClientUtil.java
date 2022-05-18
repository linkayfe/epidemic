package com.kayfe.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

    public static String doGet(String urlStr){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = null;
        try{
            httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(urlStr);
            get.setHeader("Accept","application/json");

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000).setSocketTimeout(60000).build();
            get.setConfig(requestConfig);

            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity);
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
