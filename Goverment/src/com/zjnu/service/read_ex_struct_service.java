package com.zjnu.service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.zjnu.mapper.read_ex_datastructMapper;
import com.zjnu.utils.PropertiesUtils;

public class read_ex_struct_service {

	public void read_struct(Map<String, String> message) throws IOException {

		System.out.println(message);
		// 根据用户信息修改properties文件
		PropertiesUtils.load_database_message(message, "ex_db.properties");
		// 运行mapper读取对应数据库中表的名称
		Reader reader = Resources.getResourceAsReader("conf.xml");
		SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader, "development");
		SqlSession session = sessionFacotry.openSession();
		read_ex_datastructMapper mapper = session.getMapper(read_ex_datastructMapper.class);
		try {
			List<String> list = mapper.querytablenameBymysql();
			session.commit();
			System.out.println(list);
		} catch (Exception e) {
			session.rollback();
		}finally {
			session.close();
		}

	}

}
