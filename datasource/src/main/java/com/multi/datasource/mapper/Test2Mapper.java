package com.multi.datasource.mapper;

import com.multi.datasource.entity.MyTest2;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface Test2Mapper extends Mapper<MyTest2>, MySqlMapper<MyTest2> {
}
