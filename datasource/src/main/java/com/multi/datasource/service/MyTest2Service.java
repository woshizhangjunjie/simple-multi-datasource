package com.multi.datasource.service;

import com.multi.datasource.config.DataSource;
import com.multi.datasource.config.DataSourceNames;
import com.multi.datasource.config.MultiTransactional;
import com.multi.datasource.config.MultiTransactionalHolder;
import com.multi.datasource.entity.MyTest2;
import com.multi.datasource.mapper.Test2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class MyTest2Service {

    @Autowired
    private MyTest1Service test1Service;

    @Autowired
    private Test2Mapper test2Mapper;

    private String name =  Thread.currentThread().getName();


    @Autowired
    private javax.sql.DataSource dataSource;

    @MultiTransactional//
    @DataSource(name = DataSourceNames.SECOND)
    public void datasource() throws SQLException {
        MyTest2 test = new MyTest2();
        test.setName("1");
        //数据源2
        test2Mapper.insertSelective(test);
        //数据源1
        test1Service.datasource();
        MyTest2 test2 = new MyTest2();
        test2.setName("3");
        //数据源2  如果没有使用栈的数据结构则数据源还是为1从而插入失败
        test2Mapper.insertSelective(test2);
        //int a = 1/0;
    }
}
