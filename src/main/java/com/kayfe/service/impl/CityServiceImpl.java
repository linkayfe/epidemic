package com.kayfe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kayfe.bean.City;
import com.kayfe.mapper.CityMapper;
import com.kayfe.service.CityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService  {

}
