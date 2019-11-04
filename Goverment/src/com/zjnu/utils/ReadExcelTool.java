package com.zjnu.utils;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
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
    public static void writeEXCEL( List<String[]> message, String path) {
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFSheet sheet = workbook.createSheet("数据库结构对应表");
    	
    	/*0.单元格样式*/
    	//合并的单元格样式
    	HSSFCellStyle boderStyle = workbook.createCellStyle();
    	//垂直居中
    	boderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	boderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
    	
  
    	/*1.将源数据原封不动的写进数据库里*/
    	for(int i=0;i<message.size();i++) {
    		HSSFRow row = sheet.createRow(i);
    		String[]row_message = message.get(i);
    		for(int j=0;j<row_message.length;j++) {
    			HSSFCell cell = row.createCell(j);
    			cell.setCellStyle(boderStyle);
    			String cell_message = row_message[j];
    			cell.setCellValue(cell_message);
    		}
    	}
    	
    	
    	
    	/*2.将第一列中的数据进行单元格合并*/
    	int rowCount = sheet.getLastRowNum()+1;
    	System.out.println("rowCount:"+rowCount);
    	int start = 1;
    	for(int i=2;i<rowCount;i++) {
    		//第i行第0列的单元格
    		HSSFCell now_cell = sheet.getRow(i).getCell(0);
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
    	
    	/*3.输出文件*/
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
}
