package com.kayfe.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.gson.Gson;
import com.kayfe.bean.*;
import com.kayfe.handler.GraphHandler;
import com.kayfe.service.CityService;
import com.kayfe.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class EpidemicController {

    @Autowired
    private TestService service;
    @Autowired
    private CityService cityService;

    @RequestMapping
    @CrossOrigin
    public String list(Model model) {
        LambdaQueryWrapper<DataBean> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(DataBean::getNowConfirm);
        List<DataBean> list = service.list(wrapper);
        model.addAttribute("list", list);
        List<MapConfirmBean> chinaMap = new ArrayList<>();
        List<MapNowConfirmBean> nowChinaMap = new ArrayList<>();
        for (DataBean bean : list) {
            chinaMap.add(new MapConfirmBean(bean.getAreaName(),bean.getConfirm()));
            nowChinaMap.add(new MapNowConfirmBean(bean.getAreaName(),bean.getNowConfirm()));
        }
        model.addAttribute("chinaMap",new Gson().toJson(chinaMap));
        model.addAttribute("nowChinaMap",new Gson().toJson(nowChinaMap));

        //获取折线图所需数据
        graphBeanHandle(model);
        return "graph";
    }

    @RequestMapping("/city/{province}")
    @CrossOrigin
    public String cityList(Model model, @PathVariable String province){
        //获取疫情数据所需数据
        LambdaQueryWrapper<City> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(City::getProvinceName,province);
        List<City> cityList = cityService.list(wrapper);
        model.addAttribute("list", cityList);
        model.addAttribute("title", province+"疫情情况");

        List<MapConfirmBean> confirmMap = new ArrayList<>();
        List<MapNowConfirmBean> nowConfirmMap = new ArrayList<>();
        for (City city : cityList){
            confirmMap.add(new MapConfirmBean(city.getCityName(),city.getConfirm()));
            nowConfirmMap.add(new MapNowConfirmBean(city.getCityName(),city.getNowConfirm()));
        }
        model.addAttribute("confirmMap",confirmMap);
        model.addAttribute("nowConfirmMap",nowConfirmMap);

        graphBeanHandle(model,province);
        //或取折线图所需数据
        return "province";
    }

    private void graphBeanHandle(Model model, String... province){
        List<GraphBean> graphBeans =
                province.length==0?GraphHandler.getChinaDayList():GraphHandler.getCityDayList(province[0]);
        List<String> dateList = new ArrayList<>();
        List<Integer> confirmList = new ArrayList<>();
        List<Integer> healList = new ArrayList<>();
        List<Integer> deadList = new ArrayList<>();
        List<Integer> confirmAddList = new ArrayList<>();
        for (GraphBean bean : graphBeans){
            dateList.add(bean.getDate());
            confirmList.add(bean.getConfirm());
            healList.add(bean.getHeal());
            deadList.add(bean.getDead());
            confirmAddList.add(bean.getConfirmAdd());
        }
        model.addAttribute("dateList",new Gson().toJson(dateList));
        model.addAttribute("confirmList",new Gson().toJson(confirmList));
        model.addAttribute("healList",new Gson().toJson(healList));
        model.addAttribute("deadList",new Gson().toJson(deadList));
        model.addAttribute("confirmAddList",new Gson().toJson(confirmAddList));
    }
}
