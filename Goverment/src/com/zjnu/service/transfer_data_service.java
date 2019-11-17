package com.zjnu.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.zjnu.mapper.transfer_dataMapper;
import com.zjnu.utils.PropertiesUtils;
import com.zjnu.utils.ReadExcelTool;

public class transfer_data_service {

	public void transfer_data(Map<String, String> message_local, Map<String, String> message_ex, String filepath) {
		try {
			// 得到excel中所有的数据
			List<String[]> list = ReadExcelTool.readExcel(new File(filepath));
//			for(int i=0;i<list.size();i++) {
//				String[] temp = list.get(i);
//				for(int j=0;j<temp.length;j++) {
//					System.out.println(temp[j]);
//				}
//			}
			// 1.将excel的数据进行拆分，得到连接条件字符串数组
			List<String> join_conditions = getJoin_condition(list);

//			System.out.println(join_conditions);
			// 2.得到需要从外部数据库查询的字段，按照本地的表来分
			List<List<String>> query_conditions = getQuery_condition(list);
//			System.out.println(query_conditions);
			// 3.得到需要用到的外部数据库的表
			List<Set<String>> query_tables = getTables_name(list);
//			System.out.println(query_tables);

			//4.得到需要插入的字段信息
			List<List<String>> insert_fields = getFields(list);
//			System.out.println(insert_fields);
			
			// 5.开始遍历所有需要查询的表,第i张表
			for (int i = 0; i < insert_fields.size(); i++) {
				// 5.1得到查询的表名拼接
				StringBuilder table_name = new StringBuilder();
				Set<String> one_table = query_tables.get(i);
				for (String s : one_table) {
					table_name.append(s + ",");
				}
				// 删除最后一个逗号
				table_name.deleteCharAt(table_name.length() - 1);
//				System.out.println(table_name.toString());

				// 5.2得到需要查询的字段，拼接成字符串
				StringBuilder query_condition = new StringBuilder();
				List<String> one_table_condition = query_conditions.get(i);
				for (int j = 0; j < one_table_condition.size(); j++) {
					query_condition.append(one_table_condition.get(j) + ",");
				}
				query_condition.deleteCharAt(query_condition.length() - 1);
//				System.out.println(query_condition.toString());

				// 5.3得到查询的连接条件
				int count = 0; // 用于判断条件是否足够
				StringBuilder join_condition = new StringBuilder();
				// 先放到list中
				List<String> tables_list = new ArrayList<String>();
				for (String table : one_table) {
					tables_list.add(table);
				}

				for (int j = 0; j < tables_list.size(); j++) {
					for (int k = j + 1; k < tables_list.size(); k++) {
						String table1 = tables_list.get(j);
						String table2 = tables_list.get(k);
						// 遍历连接条件，如果有这两个表，那么就加入
						for (int m = 0; m < join_conditions.size(); m++) {
							String condition = join_conditions.get(m);
							if (condition.contains(table1) && condition.contains(table2)) {
								join_condition.append(condition + ",");
								count++;
							}
						}
					}
				}
				// 看一下条件是否足够
				if (count < one_table.size() - 1) {
					JOptionPane.showMessageDialog(null, "连接条件不足，请确认连接条件是否正确");
					return;
				}
				join_condition.deleteCharAt(join_condition.length() - 1);
//				System.out.println(join_condition.toString());

				// 6.传入参数进行查询
				SqlSession session = null;
				List<Map<String,Object>> result  =null;
				PropertiesUtils.load_database_message(message_ex, "ex_db.properties");
				try {
					Reader reader = Resources.getResourceAsReader("conf.xml");
					SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader, "development");
					session = sessionFacotry.openSession();
					transfer_dataMapper mapper = session.getMapper(transfer_dataMapper.class);
					String join_condition_str = join_condition.length() > 0 ? join_condition.toString() : null;
					result = mapper.query_ex_data(query_condition.toString(),
							table_name.toString(), join_condition_str);
					System.out.println(result);
					session.commit();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "查询外部数据库信息失败");
					e.printStackTrace();
					session.rollback();
					return;
				} finally {
					session.close();
				}

				// 7.将查到的数据写入到本地数据库中
				//没有查到数据就结束
				if(result.size()==0) {
					JOptionPane.showMessageDialog(null, "外部数据库中没有查到任何信息");
					return;
				}
				
				
				PropertiesUtils.load_database_message(message_local, "ex_db.properties");
				SqlSession sqlSession = null;
				for(int j=0;j<result.size();j++) {
					/*因为使用了map，可能会改变顺序，因此要按照one_table_condition中的顺序把结果改回来*/
					List<Object> value = new ArrayList<Object>();
					for(int k=0;k<one_table_condition.size();k++) {
						System.out.println(one_table_condition.get(k));
						String name = one_table_condition.get(k).split("\\.")[1];
						value.add(result.get(j).get(name));
					}
					
					try {
						Reader reader = Resources.getResourceAsReader("conf.xml");
						SqlSessionFactory sessionFacotry = new SqlSessionFactoryBuilder().build(reader, "development");
						sqlSession = sessionFacotry.openSession();
						transfer_dataMapper mapper = sqlSession.getMapper(transfer_dataMapper.class);
						System.out.println(insert_fields.get(i).get(0));
						System.out.println(insert_fields.get(i).get(1));
						System.out.println(value.toString());
						mapper.insert_fields(insert_fields.get(i).get(0), insert_fields.get(i).get(1), value);
						sqlSession.commit();
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null, "导入数据到本地数据库失败");
						e.printStackTrace();
						sqlSession.rollback();
						return;
					}finally {
						sqlSession.close();
					}
				}
				
				
			
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接条件
	 */
	public List<String> getJoin_condition(List<String[]> list) {
		List<String> join_condition = new ArrayList<String>();
		// 跳过第一行
		for (int i = 1; i < list.size(); i++) {
			String[] temp = list.get(i);
			// 判断当前行有没有连接条件
			if (temp.length < 16)
				break;
			String eq = temp[11] + "." + temp[12] + "=" + temp[14] + "." + temp[15];
			join_condition.add(eq);
		}

		return join_condition;
	}

	/**
	 * 得到需要从外部数据库获取的数据,每张表分为一个list
	 */
	public List<List<String>> getQuery_condition(List<String[]> list) {
		List<List<String>> all_table = new ArrayList<List<String>>();
		List<String> one_table = new ArrayList<String>();
		for (int i = 1; i < list.size(); i++) {
			String[] temp = list.get(i);
			// 判断有没有表名,有的话需要将原来的list加入到all_table，然后重新初始化一个list
			if (i != 1 && temp[0].length() > 0 && !(" ".equals(temp[0]))) {
				all_table.add(one_table);
				one_table = new ArrayList<String>();
				// 将当前行加入
				if (temp.length < 8)
					continue;
				one_table.add(temp[6] + "." + temp[7]);
			}
			// 没有跳过
			else if (temp.length < 8)
				continue;
			// 其他情况就一直加
			else {

				String eq = temp[6] + "." + temp[7];
				one_table.add(eq);
			}
		}
		// 再将最后一个table加入到all_table
		all_table.add(one_table);
		return all_table;
	}

	/**
	 * 得到需要用到的外部数据库的表
	 */
	public List<Set<String>> getTables_name(List<String[]> list) {
		List<Set<String>> all_table = new ArrayList<Set<String>>();
		Set<String> one_table = new HashSet<String>();
		for (int i = 1; i < list.size(); i++) {
			String[] temp = list.get(i);
			// 判断有没有表名,有的话需要将原来的set加入到all_table，然后重新初始化一个set
			if (i != 1 && temp[0].length() > 0 && !(" ".equals(temp[0]))) {
				all_table.add(one_table);
				one_table = new HashSet<String>();
				// 将当前行加入
				if (temp.length < 8)
					continue;
				one_table.add(temp[6]);
			} else if (temp.length < 8)
				continue;
			// 其他情况就一直加
			else {
				String eq = temp[6];
				one_table.add(eq);
			}
		}
		// 再将最后一个table加入到all_table
		all_table.add(one_table);
		return all_table;
	}

	/**
	 * 得到本地需要插入的字段
	 */
	public List<List<String>> getFields(List<String[]> list) {
		List<List<String>> all_table = new ArrayList<List<String>>();
		List<String> one_table = new ArrayList<String>();
		StringBuilder fields = new StringBuilder();
		String table_name = list.get(1)[0];
		for (int i = 1; i < list.size(); i++) {
			String[] temp = list.get(i);
			// 判断有没有表名,有的话需要将原来的String根据表名加入到all_table，然后重新初始化一个string，更新表名，加入这一行的数据
			if (i != 1 && temp[0].length() > 0 && !(" ".equals(temp[0]))) {
				// 构建当前表的list
				fields = fields.deleteCharAt(fields.length() - 1);
				one_table.add(table_name);
				one_table.add(fields.toString());
				all_table.add(one_table);
				// 改变表名
				table_name = temp[0];
				fields = new StringBuilder();
				one_table = new ArrayList<String>();
				// 将第一行加入
				if (temp.length < 8)
					continue;
				fields.append(temp[2] + ",");
			}
			// 表示这个字段没有对应的外部字段，那么就不用加
			else if (temp.length < 8)
				continue;
			// 其他情况就一直加
			else {
				String eq = temp[2];
				fields.append(eq + ",");
			}
		}
		// 再将最后一个table加入到all_table
		fields = fields.deleteCharAt(fields.length() - 1);
		one_table.add(table_name);
		one_table.add(fields.toString());
		all_table.add(one_table);
		return all_table;
	}

}
