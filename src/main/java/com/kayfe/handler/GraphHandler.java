package com.kayfe.handler;

import com.google.gson.Gson;
import com.kayfe.bean.GraphBean;
import com.kayfe.redis.EpidemicRedis;
import com.kayfe.util.GetDataByUrl;
import com.kayfe.util.SerializeUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPooled;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class GraphHandler {

    private static final String url = "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?modules=chinaDayList,chinaDayAddList,nowConfirmStatis,provinceCompare";
    private static final Jedis jedis = EpidemicRedis.getJedis();

    public static List<GraphBean> getChinaDayList() {
        if (jedis.exists("china".getBytes()) && jedis.ttl("china".getBytes())>5){
            List<GraphBean> beans = (List<GraphBean>) SerializeUtil.deSerialize(jedis.get("china".getBytes()));
            if (beans != null){
                return beans;
            }
        }
        //通过GetDataByUrl类的doGet方法获取url中的json数据
        String message = GetDataByUrl.doGet(url);
        //通过gson解析json格式的数据
        Gson gson = new Gson();
        Map allMap = gson.fromJson(message, Map.class);
        //从allMap中获取所需信息
        Map data = (Map) allMap.get("data");
        ArrayList<Map> chinaDayList = (ArrayList<Map>) data.get("chinaDayList");
        //创建存放GraphBean的List
        List<GraphBean> result = getGraphBeanFromList(chinaDayList);
        jedis.setex("china".getBytes(),getTime()/1000,SerializeUtil.serialize(result));
        return result;
    }

    public static List<GraphBean> getCityDayList(String province){
        if (jedis.exists(province.getBytes()) && jedis.ttl(province.getBytes())>5){
            List<GraphBean> beans = (List<GraphBean>) SerializeUtil.deSerialize(jedis.get(province.getBytes()));
            if (beans != null) {
                return beans;
            }
        }
        String provinceUnicode = null;
        try {
            provinceUnicode = URLEncoder.encode(province,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String cityUrl = "https://api.inews.qq.com/newsqa/v1/query/pubished/daily/list?" +
                "province="+provinceUnicode+"&limit=60";
        //通过GetDataByUrl类的doGet方法获取URL中的json数据
        String message = GetDataByUrl.doGet(cityUrl);
        //通过Gson解析数据
        Gson gson = new Gson();
        Map cityDataMap = gson.fromJson(message,Map.class);
        //从cityDataMap中获取data
        ArrayList<Map> data = (ArrayList<Map>) cityDataMap.get("data");
        List<GraphBean> result = getGraphBeanFromList(data);
        jedis.setex(province.getBytes(),getTime()/1000,SerializeUtil.serialize(result));
        return result;
    }

    private static List<GraphBean> getGraphBeanFromList(ArrayList<Map> dataList){
        List<GraphBean> result = new ArrayList<>();
        for (Map tmp : dataList) {
            String date = (String) tmp.get("date");
            double confirm = (Double) tmp.get("confirm");
            double heal = (Double) tmp.get("heal");
            double dead = (Double) tmp.get("dead");
            double confirmAdd =
                    tmp.get("confirm_add")==null?0.0:Double.parseDouble((String)tmp.get("confirm_add"));
            GraphBean graphBean = new GraphBean(date,(int) confirm,(int) heal,(int) dead,(int) confirmAdd);
            result.add(graphBean);
        }
        return result;
    }

    private static Long getTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,10);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        Long tenClock = calendar.getTimeInMillis();
        return tenClock<System.currentTimeMillis()
                ?tenClock+60*60*24*1000-System.currentTimeMillis():tenClock-System.currentTimeMillis();
    }
}
