package com.multi.datasource.service;

import com.multi.datasource.config.DataSource;
import com.multi.datasource.config.DataSourceNames;
import com.multi.datasource.config.MultiTransactionalHolder;
import com.multi.datasource.entity.MyTest;
import com.multi.datasource.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class MyTest1Service {

    @Autowired
    private TestMapper testMapper;


    @DataSource(name = DataSourceNames.FIRST)
    public void datasource() throws SQLException {
        MyTest test = new MyTest();
        test.setName("2");
        testMapper.insertSelective(test);
    }
}
