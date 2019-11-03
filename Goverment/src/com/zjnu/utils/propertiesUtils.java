package com.zjnu.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class propertiesUtils {

	public static final Properties p = new Properties();
	
	public static void init(String path) {
		//转换成流
	    InputStream inputStream =     
	    		propertiesUtils.class.getClassLoader().getResourceAsStream(path);
		try {
			//从输入流中读取属性列表（键和元素对）
			p.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void update(String key, String value,String path) {
		p.setProperty(key, value);
		FileOutputStream oFile = null;
		try {
			oFile = new FileOutputStream(path);
			//将Properties中的属性列表（键和元素对）写入输出流
			p.store(oFile, "");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	
	public static void  load_database_message(Map<String,String> message , String path) {
		String username = message.get("username");
		String password = message.get("password");
		String databasename = message.get("databasename");
		String databasetype = message.get("databasetype");
		
		//加载数据库对应jar包
	}
	
}
