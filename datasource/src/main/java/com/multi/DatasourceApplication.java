package com.multi;

import com.multi.datasource.service.MyTest2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

import java.sql.SQLException;

@RestController
@MapperScan(basePackages = "com.multi.datasource.mapper")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//不屏蔽数据源会出现依赖循环
public class DatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceApplication.class, args);
    }

    @Autowired
    private MyTest2Service myTestService;

    @RequestMapping(value = "test", method = RequestMethod.POST)
    public void test() throws SQLException {
        myTestService.datasource();
    }

}
