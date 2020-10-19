package com.multi.datasource.service;

import com.multi.datasource.config.DataSource;
import com.multi.datasource.config.DataSourceNames;
import com.multi.datasource.entity.MyTest;
import com.multi.datasource.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyTest1Service {

    @Autowired
    private TestMapper testMapper;

    @DataSource(name = DataSourceNames.FIRST)
    public void datasource(){
        MyTest test = new MyTest();
        test.setName("2");
        testMapper.insertSelective(test);
    }
}
