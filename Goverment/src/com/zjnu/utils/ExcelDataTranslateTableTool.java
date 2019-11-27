package com.zjnu.utils;

import core.Column;
import core.Table;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author dxw
 * @ClassName ExcelDataTranslateTableTool.java
 * @Description ����ReadExcelTool�������ȡ���ݲ���װΪtable
 * @createTime 2019-06-05 13:52
 */
public class ExcelDataTranslateTableTool {

    /**
     * Ҫ�󼯺��е�ÿ���ַ�������ֵ����Ϊ ���� ������ ���� �ֶ����� ���� �е����� ��Ӧ��ֵ
     * ���� �� ������ ֻ��ȡ�����еĵ�һ���ַ��������ֵ
     *
     * @param data
     * @return
     */
    public static Table getTable(List<String[]> data) throws Exception {
        Table table = new Table();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                if (StringUtils.isEmpty(data.get(0)[0])) {
                    throw new Exception("�޷���excel�����л�ȡ������");
                }
                table.setTableName(data.get(0)[0]);
                table.setTableComment(data.get(0)[1]);
                Column column = getColumn(data.get(0));
                table.getColumnList().add(column);
            } else {
                table.getColumnList().add(getColumn(data.get(i)));
            }
        }
        return table;
    }

    /**
     * ���ַ��������л�ȡ���ݲ������װΪ Column ����
     *
     * @param data
     * @return
     */
    private static Column getColumn(String[] data) {
        Column column = new Column();
        //��ȡ����
        column.setColumnName(data[2]);
        //��ȡ�ֶ�����
        column.setColumnType(data[3]);
        //��ȡ�ֶγ���
        column.setColumnLength(data[4]);
        //��ȡ�ֶ�����
        column.setColumnComment(data[5]);
        return column;
    }

    public static List<Table> getTables(List<String[]> data) throws Exception {
        List<Table> tables = new ArrayList<>();
        Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();
        String tableName = "";
        int begin = 0;
        int end = 0;
        for (int i = 0; i < data.size(); i++) {
            String name = data.get(i)[0];
            //��ָ��
            if(!StringUtils.isEmpty(name) && !tableName.equals(name)){
                //�ָ�
                if(i != 0){
                    end = i;
                    map.put(tableName,data.subList(begin,end));
                    begin = i;
                }
                //��һ�ű�ı���
                tableName = name;
            }
            //�������һ����
            if(i == data.size() - 1){
                end = data.size();
                map.put(tableName,data.subList(begin,end));
            }
        }
        Set<Map.Entry<String, List<String[]>>> set = map.entrySet();
        for (Map.Entry<String, List<String[]>> stringListEntry : set) {
            List<String[]> value = stringListEntry.getValue();
            Table table = getTable(value);
            tables.add(table);
        }
        return tables;
    }
}
