package com.kayfe.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetDataByUrl {

    public static String doGet(String urlStr){
        HttpURLConnection connection = null;
        InputStream ins = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //设置
            // 连接时间:连接该url的远程主机所需时间
            // 读取时间:连接后读取完成所需时间
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.setRequestProperty("Accept","application/json");
            connection.setRequestProperty("application","x-www-form-urlencoded");
            //发送请求
            connection.connect();
            if (200==connection.getResponseCode()) {
                ins = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
                String message = "";
                while ((message = br.readLine()) != null) {
                    result.append(message);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if (ins != null) ins.close();
                if (br != null) br.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        connection.disconnect();
        return result.toString();
    }
}
