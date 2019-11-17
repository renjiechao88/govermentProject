package com.zjnu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface transfer_dataMapper {
	List<Map<String,Object>> query_ex_data(@Param("query_condition")  String query_condition, @Param("table_name") String table_name, @Param("join_condition") String join_condition);
	void insert_fields(@Param("table_name") String table_name, @Param("insert_fields") String insert_fields, @Param("value") List<Object> value);
}
