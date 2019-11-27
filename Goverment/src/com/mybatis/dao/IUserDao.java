package com.mybatis.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by FH
 * on 2019/10/27
 */
public interface IUserDao{

    @Select("select * from ${table_name}")
    List<Map<String,Object>> findAll(@Param("table_name") String tablename);

    @Insert("insert into ${table_name} (${code_name}) values (${values})")
    void insert_table (@Param("table_name") String tablename,@Param("code_name") String code_name,@Param("values") String values);

    @Select("show tables FROM ${DB_name} ")
    List<String> querytablenameBymysql(@Param("DB_name") String DB_name);

    @Select("select * from ${DB_name}")
    List<String> querytablenameByoracle(@Param("DB_name") String DB_name);

}
