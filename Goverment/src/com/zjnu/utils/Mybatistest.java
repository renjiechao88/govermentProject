package com.zjnu.utils;

import com.mybatis.dao.IUserDao;
import com.zjnu.utils.PropertiesUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FH
 * on 2019/10/27
 */
public class Mybatistest {

    public static void sqltosql(Map<String, String> message_local, Map<String, String> message_ex) throws Exception{
        //更改driver参数
        PropertiesUtils.load_database_message(message_local,"ex_db.properties");
        //读取配置文件
//        InputStream in = Resources.getResourceAsStream("SqlMapping.xml");
        InputStream in = Resources.getResourceAsStream("conf.xml");
        //创建工厂sqlsession
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //使用工厂生产对象
        SqlSession session = factory.openSession();
        //使用sqlsession创建代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);
        //使用代理对象执行方法
        //查询所有表
        String DB_name = message_local.get("databasename");
        int count = 0;
        List<String> tablename = null;
        if (message_local.get("databasetype").toLowerCase().equals("mysql")) {
        	tablename = userDao.querytablenameBymysql(DB_name);
        	count = tablename.size();
		}
        else if (message_local.get("databasetype").toLowerCase().equals("oracle")) {
        	tablename = userDao.querytablenameByoracle(DB_name);
        	count = tablename.size();
		}
        for(int i = 0;i<count;i++){
        	String tableName = tablename.get(i);
        	List<Map<String,Object>> users = userDao.findAll(tableName);
        	System.out.println(users);
            //更改driver参数
            PropertiesUtils.load_database_message(message_ex,"ex_db.properties");
            //读取配置文件
            in = Resources.getResourceAsStream("conf.xml");
            //创建工厂sqlsession
            builder = new SqlSessionFactoryBuilder();
            factory = builder.build(in);
            //使用工厂生产对象
            session = factory.openSession();
            //使用sqlsession创建代理对象
            userDao = session.getMapper(IUserDao.class);
            String sub_code_name;
            //处理数据并执行sql语句
            for(int j = 0;j<users.size();j++){
                StringBuilder code_name = new StringBuilder(1000);
                StringBuilder values = new StringBuilder(1000);
                sub_code_name = users.get(j).toString();
                String[] sub_data = sub_code_name.split(",");
                for(int k = 0;k<sub_data.length;k++){
                    String[] name = sub_data[k].split("=");
                    code_name.append(name[0]+",");
                    values.append("'"+name[1]+"',");
                }
                int m = code_name.lastIndexOf(",");
                code_name.delete(m,m+1);
                code_name.delete(0,1);
                int f = values.lastIndexOf(",");
                values.delete(f,f+1);
                values.delete(f-2,f-1);
                System.out.println(tableName);
                System.out.println(code_name.toString());
                System.out.println(values.toString());
                userDao.insert_table(tableName,code_name.toString(),values.toString());
            }
            //储存数据
            session.commit();
            //回到最初本地数据库配置
            PropertiesUtils.load_database_message(message_local,"ex_db.properties");
            //读取配置文件
            in = Resources.getResourceAsStream("conf.xml");
            //创建工厂sqlsession
            builder = new SqlSessionFactoryBuilder();
            factory = builder.build(in);
            //使用工厂生产对象
            session = factory.openSession();
            //使用sqlsession创建代理对象
            userDao = session.getMapper(IUserDao.class);
        }
        
        
        

        //释放资源
        session.close();
        in.close();
    }

//    public static void main(String[] args) throws Exception {
//    	Map<String,String> message_local = new HashMap<String, String>();
//    	message_local.put("username", "root");
//    	message_local.put("password", "960206");
//    	message_local.put("databasename", "test");
//    	message_local.put("databasetype", "mysql");
//    	message_local.put("ipaddress", "localhost");
//    	Map<String,String> message_ex = new HashMap<String, String>();
//    	message_ex.put("username", "root");
//    	message_ex.put("password", "960206");
//    	message_ex.put("databasename", "test2");
//    	message_ex.put("databasetype", "mysql");
//    	message_ex.put("ipaddress", "localhost");
//    	sqltosql(message_local, message_ex);
//	}

}
