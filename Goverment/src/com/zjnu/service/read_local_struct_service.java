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
import com.zjnu.mapper.read_local_datastructMapper;
import com.zjnu.utils.PropertiesUtils;
import com.zjnu.utils.ReadExcelTool;

public class read_local_struct_service {
public void read_struct(Map<String, String> message) throws IOException {
		
		/*0.初始化我们需要用到的变量*/
		List<String>  tableNames= new ArrayList<String>();
		int count = 0;
		List<String[]> data = new ArrayList<String[]>();//讲最后的数据拼接为data
		//填写头部的信息
		String[] head = {"表名","表描述","字段名","字段类型","长度","字段描述","外部表名称","外部字段名称"};
		data.add(head);
		
		// 1.根据用户信息修改properties文件
		PropertiesUtils.load_database_message(message, "ex_db.properties");
		
		//2. 运行mapper读取对应数据库中表的名称和表的结构
		Reader reader = Resources.getResourceAsReader("conf.xml");
		SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader, "development");
		SqlSession session = sessionFacotry.openSession();
		try {
			String databaseType = message.get("databasetype").toLowerCase();
			read_local_datastructMapper mapper = session.getMapper(read_local_datastructMapper.class);
			
			/*2.1 为mysql数据库*/
			if("mysql".equals(databaseType)) {
				tableNames = mapper.querytablenameBymysql();
				//根据表名称查询表的字段属性
				for(int i=0;i<tableNames.size();i++) {
					List<Column> columns =  mapper.queryColumnBymysql(tableNames.get(i));
					/*将column的数据拆开来*/
					for(int j=0;j<columns.size();j++) {
						//拼接成我们需要的格式
						String[] temp = {"","","","","",""};
						if(j==0) {
							temp[0] = tableNames.get(i);//第一次的时候需要记录表名
							temp[1] = tableNames.get(i)+"的描述信息";
						}
						Column column = columns.get(j);
						temp[2] = column.getField();
						//mysql的数据长度包含在type中，需要对type进行切分
						String type = column.getType();
						int index_start = type.indexOf("(");
						int index_end = type.indexOf(")");
						temp[3] = type.substring(0, index_start);
						temp[4] = type.substring(index_start+1, index_end);
						
						data.add(temp);
					}
				}
				
			}
		
			/*2.2 为oracle数据库*/
			if("oracle".equals(databaseType)) {
				tableNames = mapper.querytablenameByoracle();
				//根据表名称查询表的字段属性
				for(int i=0;i<tableNames.size();i++) {
					List<Column> columns =  mapper.queryColumnByoracle(tableNames.get(i));
					for(int j=0;j<columns.size();j++) {
						//拼接成我们需要的格式
						String[] temp = {"","","","","",""};
						if(j==0) {
							temp[0] = tableNames.get(i);//第一次的时候需要记录表名
							temp[1] = tableNames.get(i)+"的描述信息";
						}
						Column column = columns.get(j);
						temp[2] = column.getField();
						//oracle的数据长度可以直接获取
						temp[3] = column.getType();
						temp[4] = column.getLength();
						
						data.add(temp);
					}
				}
			}
			
			/*2.3为sqlserver数据库*/
			
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}finally {
			session.close();
		}
	
		for(int i=0;i<data.size();i++) {
			String[] temp = data.get(i);
			for(int j=0;j<temp.length;j++) {
				System.out.println(temp[j]);
			}
		}
	
	
		
		//3.将内容写入excel
		ReadExcelTool.writeExcel(data, message.get("filepath"));
		
	}
}
