package com.zjnu.utils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.zjnu.entity.Column;

import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

/**
 * @author dxw
 * @ClassName ReadExcelTool.java
 * @Description
 * @createTime 2019-06-05 12:12
 */
public class ReadExcelTool {

  
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    public static List<String[]> readExcel(File file) throws IOException {
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getLastCellNum();
                    String[] cells = new String[row.getLastCellNum()];
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
        }
//        logger.info(gson.toJson(list));
        return list;
    }
    public static void checkFile(File file) throws IOException{
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getName();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "不是excel文件");
        }
    }
    public static Workbook getWorkBook(File file){
        //获得文件名
        String fileName = file.getName();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = new FileInputStream(file);
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
        }
        return workbook;
    }
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
    
    
    /*按照格式输出文件*/
    public static void writeEXCEL( List<String[]> message, String path,List<String> tablenames,List<List<Column>> columns) {
    	XSSFWorkbook workbook = new XSSFWorkbook();
    	XSSFSheet sheet = workbook.createSheet("数据库结构对应表");
    	
    	/*0.单元格样式*/
    	//合并的单元格样式
    	CellStyle boderStyle = workbook.createCellStyle();
    	//垂直居中
//    	boderStyle.setAlignment(CellStyle.);
    	
  
    	/*1.将源数据原封不动的写进数据库里*/
    	for(int i=0;i<message.size();i++) {
    		XSSFRow row = sheet.createRow(i);
    		String[]row_message = message.get(i);
    		for(int j=0;j<row_message.length;j++) {
    			XSSFCell cell = row.createCell(j);
    			cell.setCellStyle(boderStyle);
    			String cell_message = row_message[j];
    			cell.setCellValue(cell_message);
    		}
    	}
    	
    	/*2.将表名和字段名进行二级下拉框添加*/
    	Cascade(workbook,sheet,tablenames,columns);
    	
    	
    	/*3.将第一列和第二列中的数据进行单元格合并*/
    	int rowCount = sheet.getLastRowNum()+1;
    	System.out.println("rowCount:"+rowCount);
    	int start = 1;
    	for(int i=2;i<rowCount;i++) {
    		//第i行第0列的单元格
    		XSSFCell now_cell = sheet.getRow(i).getCell(0);
    		String now_string = now_cell.getStringCellValue().trim();
    		//不为空时合并上方单元格(包括第一列和第二列)，为空格时继续向下
    		if(!(now_string.length()<=0 || " ".equals(now_string))) {
    			CellRangeAddress region = new CellRangeAddress(start,i-1,0,0);
    			CellRangeAddress region2 = new CellRangeAddress(start,i-1,1,1);
    			sheet.addMergedRegion(region);
    			sheet.addMergedRegion(region2);
    			start = i;
    		}
    	}
    	
    	/*4.输出文件*/
    	OutputStream outputStream = null ;
		try {
			outputStream = new FileOutputStream(new File(path));
			workbook.write(outputStream);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "文件输出失败");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "文件输出失败");
			e.printStackTrace();
		}finally {
			if(outputStream!=null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "文件输出流关闭失败");
					e.printStackTrace();
				}
			}
		}
    }
    
    
    
    /*关于二级联动下拉框的创建*/
    public static void Cascade(XSSFWorkbook book,XSSFSheet sheetPro,List<String> tablenames, List<List<Column>> columns) { 

  
        //创建一个专门用来存放地区信息的隐藏sheet页  
        //因此也不能在现实页之前创建，否则无法隐藏。  
        Sheet hideSheet = book.createSheet("area");  
        //这一行作用是将此sheet隐藏，功能未完成时注释此行,可以查看隐藏sheet中信息是否正确
        //book.setSheetHidden(book.getSheetIndex(hideSheet), true);  

        int rowId = 0;  
        // 设置第一行，存储所有的表名 
        Row tablenameRow = hideSheet.createRow(rowId++);  
        tablenameRow.createCell(0).setCellValue("数据库表名");  
        for(int i = 0; i < tablenames.size(); i ++){  
            Cell tablenameCell = tablenameRow.createCell(i + 1);  
            tablenameCell.setCellValue(tablenames.get(i));  
        }  
        // 将具体的数据写入到每一行中，行开头为父级区域，后面是子区域。 即第一列是表名后面是字段名 
       for(int i = 0;i < tablenames.size();i++){  
            String key = tablenames.get(i);  
            List<Column> son = columns.get(i); 
            Row row = hideSheet.createRow(rowId++);  
            row.createCell(0).setCellValue(key);  
            for(int j = 0; j < son.size(); j ++){  
                Cell cell = row.createCell(j + 1);  
                cell.setCellValue(son.get(j).getField());  
            }  

            // 添加名称管理器,为表名分配后续的字段名  
            String range = getRange(1, rowId, son.size());  
            Name name = book.createName(); 
            //key不可重复
            name.setNameName(key);  
            String formula = "area!" + range;  
            name.setRefersToFormula(formula);  
        }  

        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheetPro);  
        // 表名规则  
        DataValidationConstraint provConstraint = dvHelper.createExplicitListConstraint(list2String(tablenames));
        // 四个参数分别是：起始行、终止行、起始列、终止列，表名在第六列，
        CellRangeAddressList provRangeAddressList = new CellRangeAddressList(1, sheetPro.getLastRowNum(), 6, 6);  
        DataValidation provinceDataValidation = dvHelper.createValidation(provConstraint, provRangeAddressList);  
        //验证
        provinceDataValidation.createErrorBox("error", "请选择正确的表名");  
        provinceDataValidation.setShowErrorBox(true);  
        provinceDataValidation.setSuppressDropDownArrow(true);  
        sheetPro.addValidationData(provinceDataValidation);  

        //对所有行行设置有效性
         for(int i = 1;i < sheetPro.getLastRowNum()+1;i++){
             setDataValidation("G" ,sheetPro,i,8);
         } 
         
         return;
    } 


    /**
     * 设置有效性
     * @param offset 主影响单元格所在列，即此单元格由哪个单元格影响联动
     * @param sheet
     * @param rowNum 行数
     * @param colNum 列数
     */
    public static void setDataValidation(String offset,XSSFSheet sheet, int rowNum,int colNum) {
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        DataValidation data_validation_list;
            data_validation_list = getDataValidationByFormula(
                    "INDIRECT($" + offset + (rowNum) + ")", rowNum, colNum,dvHelper);
        sheet.addValidationData(data_validation_list);
    }

    /**
     * 加载下拉列表内容
     * @param formulaString
     * @param naturalRowIndex
     * @param naturalColumnIndex
     * @param dvHelper
     * @return
     */
    private static  DataValidation getDataValidationByFormula(
            String formulaString, int naturalRowIndex, int naturalColumnIndex,XSSFDataValidationHelper dvHelper) {
        // 加载下拉列表内容
        // 举例：若formulaString = "INDIRECT($A$2)" 表示规则数据会从名称管理器中获取key与单元格 A2 值相同的数据，
        //如果A2是江苏省，那么此处就是江苏省下的市信息。 
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(formulaString);
        // 设置数据有效性加载在哪个单元格上。
        // 四个参数分别是：起始行、终止行、起始列、终止列
        int firstRow = naturalRowIndex -1;
        int lastRow = naturalRowIndex - 1;
        int firstCol = naturalColumnIndex - 1;
        int lastCol = naturalColumnIndex - 1;
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                lastRow, firstCol, lastCol);
        // 数据有效性对象
        // 绑定
        XSSFDataValidation data_validation_list = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, regions);
        data_validation_list.setEmptyCellAllowed(false);
        if (data_validation_list instanceof XSSFDataValidation) {
            data_validation_list.setSuppressDropDownArrow(true);
            data_validation_list.setShowErrorBox(true);
        } else {
            data_validation_list.setSuppressDropDownArrow(false);
        }
        // 设置输入信息提示信息
        data_validation_list.createPromptBox("下拉选择提示", "请使用下拉方式选择合适的值！");
        // 设置输入错误提示信息
        //data_validation_list.createErrorBox("选择错误提示", "你输入的值未在备选列表中，请下拉选择合适的值！");
        return data_validation_list;
    }

    /** 
     *  计算formula
     * @param offset 偏移量，如果给0，表示从A列开始，1，就是从B列 
     * @param rowId 第几行 
     * @param colCount 一共多少列 
     * @return 如果给入参 1,1,10. 表示从B1-K1。最终返回 $B$1:$K$1 
     *  
     */  
    public static String getRange(int offset, int rowId, int colCount) {  
        char start = (char)('A' + offset);  
        if (colCount <= 25) {  
            char end = (char)(start + colCount - 1);  
            return "$" + start + "$" + rowId + ":$" + end + "$" + rowId;  
        } else {  
            char endPrefix = 'A';  
            char endSuffix = 'A';  
            if ((colCount - 25) / 26 == 0 || colCount == 51) {// 26-51之间，包括边界（仅两次字母表计算）  
                if ((colCount - 25) % 26 == 0) {// 边界值  
                    endSuffix = (char)('A' + 25);  
                } else {  
                    endSuffix = (char)('A' + (colCount - 25) % 26 - 1);  
                }  
            } else {// 51以上  
                if ((colCount - 25) % 26 == 0) {  
                    endSuffix = (char)('A' + 25);  
                    endPrefix = (char)(endPrefix + (colCount - 25) / 26 - 1);  
                } else {  
                    endSuffix = (char)('A' + (colCount - 25) % 26 - 1);  
                    endPrefix = (char)(endPrefix + (colCount - 25) / 26);  
                }  
            }  
            return "$" + start + "$" + rowId + ":$" + endPrefix + endSuffix + "$" + rowId;  
        }  
    }  
    
    public static String[] list2String(List<String> list) {
    	String[] result = new String[list.size()+1];
    	for(int i=0;i<list.size();i++) {
    		result[i] = list.get(i);
    	}
    	
    	return result;
    }

}
