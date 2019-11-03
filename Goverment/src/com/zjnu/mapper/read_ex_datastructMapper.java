package com.zjnu.mapper;

import java.util.List;

import com.zjnu.entity.Column;

public interface read_ex_datastructMapper {

	List<String> querytablenameBymysql();
	List<Column> queryColumn(String tablename);
	
}
