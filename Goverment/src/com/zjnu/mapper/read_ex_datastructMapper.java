package com.zjnu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zjnu.entity.Column;

public interface read_ex_datastructMapper {

	List<String> querytablenameBymysql();
	List<Column> queryColumnBymysql(@Param("tablename") String tablename);

}
