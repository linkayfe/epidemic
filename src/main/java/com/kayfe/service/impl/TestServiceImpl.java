package com.kayfe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kayfe.bean.DataBean;
import com.kayfe.mapper.TestMapper;
import com.kayfe.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, DataBean> implements TestService {

//    @Override
//    public List<DataBean> list() {
////        return TestHandler.list();
//        return JsoupHandler.list();
//    }
}
