package com.multi.datasource.mapper;

import com.multi.datasource.entity.MyTest;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface TestMapper extends Mapper<MyTest>, MySqlMapper<MyTest> {
}
