package com.zjnu.utils;

import core.Table;
import core.TableThread;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.zjnu.utils.ExcelDataTranslateTableTool;
import com.zjnu.utils.ReadExcelTool;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dxw
 * @ClassName Main.java
 * @Description 系统运行入口
 * @createTime 2019-06-05 14:11
 */
public class exceltosqlTool {

    public static void exceltosql(Map<String, String> message_ex,File tableTemplate) throws Exception {
        try {
            //连接到drive
        	PropertiesUtils.load_database_message(message_ex,"ex_db.properties");
            //获取表格数据
        	
            List<String[]> data = ReadExcelTool.readExcel(tableTemplate);
            //获取数据库配置文件
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("conf.xml"));
            //获取自定义参数化配置
            Properties custom = Resources.getResourceAsProperties("setting.properties");
            String createTableMany = custom.getProperty("create_table_many");
            if(createTableMany.equals("1")){
                List<Table> tables = ExcelDataTranslateTableTool.getTables(data);
                ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
                threadPoolExecutor.prestartCoreThread();
                for (Table table : tables) {
                    SqlBuilder sqlBuilder = new SqlBuilder(table, custom);
                    TableThread tableThread = new TableThread(sqlBuilder, sqlSessionFactory);
                    threadPoolExecutor.execute(tableThread);
                }
                threadPoolExecutor.shutdown();
            }else {
                //解析数据
                Table table = ExcelDataTranslateTableTool.getTable(data);
                //获取连接
                SqlSession sqlSession = sqlSessionFactory.openSession();
                //创建sql
                SqlBuilder sqlBuilder = new SqlBuilder(table,custom);
                String sql = sqlBuilder.getCreateTableSql();
                System.out.println(sql);
                //执行操作
                PreparedStatement preparedStatement = sqlSession.getConnection().prepareStatement(sql);
                preparedStatement.executeUpdate();
                //关闭
                if(preparedStatement != null){
                    preparedStatement.close();
                }
                if(sqlSession != null ){
                    sqlSession.close();
                }
            }
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }
}
