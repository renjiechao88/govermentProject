package com.zjnu.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.zjnu.entity.Column;
import com.zjnu.mapper.read_ex_datastructMapper;
import com.zjnu.utils.ExcelUtils;
import com.zjnu.utils.PropertiesUtils;
import com.zjnu.utils.ReadExcelTool;

public class read_ex_struct_service {

	public void read_struct(Map<String, String> message) throws IOException {
		
		//最后查询到的信息
		List<String>  tableNames= new ArrayList<String>();
		List<List<Column>> all_columns = new ArrayList<List<Column>>();
		
		// 1.根据用户信息修改properties文件
		PropertiesUtils.load_database_message(message, "ex_db.properties");
		
		//2. 运行mapper读取对应数据库中表的名称和表的结构
		Reader reader = Resources.getResourceAsReader("conf.xml");
		SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader, "development");
		SqlSession session = sessionFacotry.openSession();
		try {
			read_ex_datastructMapper mapper = session.getMapper(read_ex_datastructMapper.class);
			tableNames = mapper.querytablenameBymysql();
			//根据表名称查询表的字段属性
			for(int i=0;i<tableNames.size();i++) {
				List<Column> column =  mapper.queryColumnBymysql(tableNames.get(i));
				all_columns.add(column);
			}
			session.commit();
			
		} catch (Exception e) {
			session.rollback();
		}finally {
			session.close();
		}
	
		
		//3.读取excel文件，开始写入二 级菜单
//		System.out.println(tableNames);
//		System.out.println(all_columns);
//		System.out.println(message.get("filepath"));
		File file = new File(message.get("filepath"));
		List<String[]> list = ReadExcelTool.readExcel(file);
		
		for(int i=0;i<list.size();i++) {
			for(int j=0;j<list.get(i).length;j++)
			System.out.println(list.get(i)[j]);
		}
		
		ReadExcelTool.writeEXCEL(list, "C:\\Users\\renjiechao\\Desktop\\test111.xls");
	}

}
