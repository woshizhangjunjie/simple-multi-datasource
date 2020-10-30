package com.multi.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.multi.datasource.proxy.DatasourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DynamicDataSourceConfig {
    @Bean("firstDataSource")
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource(){
        return new DruidDataSource();
    }

    @Bean("secondDataSource")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource(){
        return new DruidDataSource();
    }

    @Bean("firstDataSourceProxy")
    public DataSource firstDataSourceProxy() {
        return new DatasourceProxy(firstDataSource());
    }

    @Bean("secondDataSourceProxy")
    public DataSource secondDataSourceProxy() {
        return new DatasourceProxy(secondDataSource());
    }


    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource firstDataSourceProxy, DataSource secondDataSourceProxy ) {
        Map<String, DataSource> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSourceProxy);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSourceProxy);
        return new DynamicDataSource(firstDataSourceProxy, targetDataSources);
    }

}
