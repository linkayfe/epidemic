package com.kayfe.handler;

import com.google.gson.Gson;
import com.kayfe.bean.City;
import com.kayfe.bean.DataBean;
import com.kayfe.service.CityService;
import com.kayfe.service.TestService;
import com.kayfe.util.GetDataByUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TenEpidemicHandler {

    private static final String EPIDEMIC_URL =
            "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?modules=statisGradeCityDetail,diseaseh5Shelf";

    @Autowired
    private TestService service;
    @Autowired
    private CityService cityService;

    //该注解修饰的方法会在，项目启动时执行一次
    @PostConstruct
    public void dataInit(){
        List<DataBean> beans = list().get("province");
        service.remove(null);
        service.saveBatch(beans);
        List<City> cities = list().get("city");
        cityService.remove(null);
        cityService.saveBatch(cities);
    }

    //方法执行后10秒再执行
    //@Scheduled(fixedRate = 10000)
    //方法执行完毕之后10秒再执行
    //@Scheduled(fixedDelay = 10000)
    //每天12点执行
    @Scheduled(cron = "0 0 12 * * ?")
    public void updateData(){
        List<DataBean> list = list().get("province");
        service.remove(null);
        service.saveBatch(list);
        List<City> cities = list().get("city");
        cityService.remove(null);
        cityService.saveBatch(cities);
    }

    private static Map<String,ArrayList> list(){
        Map<String,ArrayList> result = new HashMap<>();
        //通过url获取信息
        String message = GetDataByUrl.doGet(EPIDEMIC_URL);
        //通过gson解析数据
        Gson gson = new Gson();
        Map map = gson.fromJson(message, Map.class);
        //该数据的格式由父到子的顺序为：message-->data-->areaTree
        //areaTree中有一个列表和一个时间的键值对
        //列表一共有34项，表示省份
        //省份中有键值对today,children,name,date,total,children中包含该省份城市的数据
        //从map中取出data
        String dataStr = gson.toJson(map.get("data"));
        //gson解析dataStr
        Map dataMap = gson.fromJson(dataStr, Map.class);
        String diseaseStr = gson.toJson(dataMap.get("diseaseh5Shelf"));
        Map diseaseMap = gson.fromJson(diseaseStr,Map.class);
        ArrayList areaTree = (ArrayList) diseaseMap.get("areaTree");
        Map areaTreeMap = (Map) areaTree.get(0);
        ArrayList childrenList = (ArrayList) areaTreeMap.get("children");
        ArrayList<DataBean> provinceList = new ArrayList<>();
        ArrayList<City> cityList = new ArrayList<>();
        for (int i=0;i< childrenList.size();i++){
            Map tmp = (Map)childrenList.get(i);
            DataBean bean = (DataBean) getDataBean(tmp);
            provinceList.add(bean);
            ArrayList province = (ArrayList) tmp.get("children");
            for (int j = 0; j < province.size(); j++) {
                Map cityMap = (Map) province.get(j);
                City city = (City) getDataBean(cityMap,bean.getAreaName());
                if (city.getNowConfirm()>=0) {
                    cityList.add(city);
                }
            }
        }
        result.put("province",provinceList);
        result.put("city",cityList);
        return result;
    }

    private static Object getDataBean(Map dataMap,String... province){
        String name= (String) dataMap.get("name");
        Map today = (Map) dataMap.get("today");
        double confirmAdd = (Double) today.get("confirm");
        Map total = (Map) dataMap.get("total");
        double confirm = (Double) total.get("confirm");
        double nowConfirm = (Double) total.get("nowConfirm");
        double dead = (Double) total.get("dead");
        double heal = (Double) total.get("heal");
        String updateTime = (String) total.get("mtime");
        if (province.length==0)
            return new DataBean(null,name,(int)confirmAdd, (int)confirm,
                    (int) nowConfirm,(int) dead,(int) heal,updateTime);
        else
            return new City(null,name,(int)confirmAdd, (int)confirm,
                    (int) nowConfirm,(int) dead,(int) heal,province[0],updateTime);
    }
}
