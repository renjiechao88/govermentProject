package com.zjnu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    public static void main(String[] args){
        ExcelUtils excelUtil = new ExcelUtils();
        //读取excel数据
        ArrayList<Map<String,String>> result = excelUtil.readExcelToObj("d:\\first\\a.xlsx");
        for(Map<String,String> map:result){
            System.out.println("输出："+map);
        }
    }
    /**
     * 读取excel数据
     * @param path
     */
    public ArrayList<Map<String,String>> readExcelToObj(String path) {
    	File file = new File(path);
    	String fileName = file.getName();
        boolean isE2007 = false;    //判断是否是excel2007格式
        if(fileName.endsWith("xlsx"))
            isE2007 = true;
        
        Workbook wb = null;
        ArrayList<Map<String,String>> result = null;
        try {
        	InputStream inputStream = new FileInputStream(file);
            //根据文件格式(2003或者2007)来初始化
            if(isE2007)
                wb = new XSSFWorkbook(inputStream);
            else
            	wb = new HSSFWorkbook(inputStream);
           
            result = readExcel(wb, 0, 1, 0);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
 
    /**
     * 读取excel文件
     * @param wb
     * @param sheetIndex sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine 去除最后读取的行
     */
    private ArrayList<Map<String,String>> readExcel(Workbook wb,int sheetIndex, int startReadLine, int tailLine) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        ArrayList<Map<String,String>> result = new ArrayList<Map<String,String>>();
        for(int i=startReadLine; i<sheet.getLastRowNum()-tailLine+1; i++) {
 
            row = sheet.getRow(i);
            Map<String,String> map = new HashMap<String,String>();
            for(Cell c : row) {
                String returnStr = "";
                boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
                //判断是否具有合并单元格
                if(isMerge) {
                    String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
//                    System.out.print(rs + "------ ");
                    returnStr = rs;
                }else {
//                    System.out.print(c.getRichStringCellValue()+"++++ ");
                	System.out.println(c.getColumnIndex());
                    returnStr = c.getRichStringCellValue().getString();
                }
                
                map.put("id"+c.getColumnIndex(), returnStr);
        
            }
            result.add(map);
        }
        return result;
    }
 
    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }
        return null ;
    }
 
    /**
     * 判断合并了行
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private boolean isMergedRow(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row == firstRow && row == lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }
 
    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }
 
    /**
     * 判断sheet页中是否含有合并单元格
     * @param sheet
     * @return
     */
    private boolean hasMerged(Sheet sheet) {
        return sheet.getNumMergedRegions() > 0 ? true : false;
    }
 
    /**
     * 合并单元格
     * @param sheet
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     */
    private void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }
 
    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell){
 
        if(cell == null) return "";
 
        if(cell.getCellType() == Cell.CELL_TYPE_STRING){
 
            return cell.getStringCellValue();
 
        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
 
            return String.valueOf(cell.getBooleanCellValue());
 
        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
 
            return cell.getCellFormula() ;
 
        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
 
            return String.valueOf(cell.getNumericCellValue());
 
        }
        return "";
    }
    /**
     * 从excel读取内容
     */
    public static void readContent(String fileName)  {
        boolean isE2007 = false;    //判断是否是excel2007格式
        if(fileName.endsWith("xlsx"))
            isE2007 = true;
        try {
            InputStream input = new FileInputStream(fileName);  //建立输入流
            Workbook wb  = null;
            //根据文件格式(2003或者2007)来初始化
            if(isE2007)
                wb = new XSSFWorkbook(input);
            else
            	wb = new HSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器
            while (rows.hasNext()) {
                Row row = rows.next();  //获得行数据
                System.out.println("Row #" + row.getRowNum());  //获得行号从0开始
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    System.out.println("Cell #" + cell.getColumnIndex());
                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            System.out.println(cell.getNumericCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            System.out.println(cell.getStringCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            System.out.println(cell.getBooleanCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            System.out.println(cell.getCellFormula());
                            break;
                        default:
                            System.out.println("unsuported sell type======="+cell.getCellType());
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    /**
     * 	将读取到的内容写回EXCEL文件
     */
    public  static void writeEXCEL( ArrayList<Map<String,String>> message, String path) {
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFSheet sheet = workbook.createSheet("数据库结构对应表");
    	/*1.将源数据原封不动的写进数据库里*/
    	for(int i=0;i<message.size();i++) {
    		HSSFRow row = sheet.createRow(i);
    		Map<String,String> row_message = message.get(i);
    		for(int j=0;j<row_message.size();j++) {
    			HSSFCell cell = row.createCell(j);
    			String cell_message = row_message.get("id"+j);
    			cell.setCellValue(cell_message);
    		}
    	}
    	
    	
    	
    	/*2.将第一列中的数据进行单元格合并*/
    	int rowCount = sheet.getLastRowNum()+1;
    	System.out.println("rowCount:"+rowCount);
    	int start = 1;
    	for(int i=2;i<rowCount;i++) {
    		//第i行第0列的单元格
    		HSSFCell pre_cell = sheet.getRow(i-1).getCell(0);
    		HSSFCell now_cell = sheet.getRow(i).getCell(0);
    		String pre_string = pre_cell.getStringCellValue();
    		String now_string = now_cell.getStringCellValue();
    		//不同就重置start，同时合并上面相同数值单元格，相同就继续往下
    		if(!pre_string.equals(now_string)) {
    			CellRangeAddress region = new CellRangeAddress(start,i-1,0,0);
    			sheet.addMergedRegion(region);
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
