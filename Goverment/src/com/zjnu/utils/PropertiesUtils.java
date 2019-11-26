package com.zjnu.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

	public static final Properties p = new Properties();
	public static final String oracle_drive = "oracle.jdbc.OracleDriver";
	public static final String mysql_drive = "com.mysql.jdbc.Driver";	
	public static final String sqlserver_drive = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	public static String oracle_string = "jdbc:oracle:thin:@ipaddress:1521:name";
	public static String mysql_string = "jdbc:mysql://ipaddress:3306/name";
	public static String sqlserver_string = "jdbc:microsoft:sqlserver:ipaddress:1433;databasename=name";

	
	
	public static void init(String path) {
		//转换成流
	    InputStream inputStream =     
	    		PropertiesUtils.class.getClassLoader().getResourceAsStream(path);
		try {
			//从输入流中读取属性列表（键和元素对）
			p.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void update(String key, String value ,String path) {
		p.setProperty(key, value);
		FileOutputStream oFile = null;
		try {
			String filePath = PropertiesUtils.class.getClassLoader().getResource(path).getPath();
//			System.out.println(filePath);
			oFile = new FileOutputStream(filePath);
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
	

	public static Map<String,String> list(String path) {
		Map<String,String> message = new HashMap<String, String>();
		init(path);
		Enumeration en = p.propertyNames(); //得到配置文件的名字
		while(en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = p.getProperty(strKey);
			message.put(strKey, strValue);
		}
		return message;
	}

	
//	public static void commit(String path) {
//		FileOutputStream oFile = null;
//		try {
//			oFile = new FileOutputStream(path);
//			//将Properties中的属性列表（键和元素对）写入输出流
//			p.store(oFile, "");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				oFile.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}


	
	public static void  load_database_message(Map<String,String> message , String path) {
		//加载properties文件
		init(path);
//		list();
		String username = message.get("username");
		String password = message.get("password");
		String databasename = message.get("databasename");
		String databasetype = message.get("databasetype").toLowerCase();
		String ipaddress = message.get("ipaddress");
		
		//加载数据库对应jar包,拼接字符串
		if("oracle".equals(databasetype)) {
			update("driver",oracle_drive,path);
			update("url",oracle_string.replaceFirst("ipaddress", ipaddress).replaceFirst("name", databasename),path);
		}
		else if("mysql".equals(databasetype)) {
			update("dirve", mysql_drive,path);
			update("url",mysql_string.replaceFirst("ipaddress", ipaddress).replaceFirst("name", databasename),path);

		}else {
			update("drive", sqlserver_drive,path);
			update("url",sqlserver_string.replaceFirst("ipaddress", ipaddress).replaceFirst("name", databasename),path);

		}
		
		//加载数据库用户名和密码
		update("username", username,path);
		update("password",password,path);


	}
	
	public static void write_file(Map<String,String> message, String path) {
		String username = message.get("username");
		String password = message.get("password");
		String databasename = message.get("databasename");
		String databasetype = message.get("databasetype").toLowerCase();
		String ipaddress = message.get("ipaddress");
		
		update("username", username, path);
		update("password", password, path);
		update("databasename", databasename, path);
		update("databasetype", databasetype, path);
		update("ipaddress", ipaddress, path);
		
	}
	
}
