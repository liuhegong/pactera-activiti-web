package com.pactera.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pactera on 2017/11/29.
 */
public class ExportUtil {
   private SXSSFWorkbook wb = null;

   private Sheet sheet = null;

   private String sheetName;

   private String[] headers;

   private List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();

   public ExportUtil(SXSSFWorkbook wb, Sheet sheet) {
      this.wb = wb;
      this.sheet = sheet;
   }

   public ExportUtil(String sheetName, String[] headers, List<Map<String,Object>> dataList) {
      this.dataList = dataList;
      this.headers = headers;
      this.sheetName = sheetName;
   }
   public void setRegionStyle(CellRangeAddress region, XSSFCellStyle cs) {
      int toprowNum = region.getFirstRow();
      for (int i = toprowNum; i <= region.getLastRow(); i++) {
         SXSSFRow row = (SXSSFRow) sheet.getRow(i);

         for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
            SXSSFCell cell = (SXSSFCell) row.getCell(j);
            cell.setCellStyle(cs);
         }
      }
   }

   // 设置表头的单元格样式
   public CellStyle getHeadStyle() {
      // 创建单元格样式
      CellStyle cellStyle = wb.createCellStyle();
      // 设置单元格的背景颜色为淡蓝色
      cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
      // 设置填充字体的样式
      cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
      // 设置单元格居中对齐
      cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
      // 设置单元格垂直居中对齐
      cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
      // 创建单元格内容显示不下时自动换行
      cellStyle.setWrapText(true);
      // 设置单元格字体样式
      XSSFFont font = (XSSFFont) wb.createFont();
      font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 这是字体加粗
      font.setFontName("宋体");// 设置字体的样式
      font.setFontHeight(14);// 设置字体的大小
      cellStyle.setFont(font);// 将字体填充到表格中去
      // 设置单元格边框为细线条（上下左右）
      cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
      return cellStyle;
   }
   // 设置表头的单元格样式
   public CellStyle getHeadStyle(SXSSFWorkbook workbook) {
      // 创建单元格样式
      CellStyle cellStyle = workbook.createCellStyle();
      // 设置单元格的背景颜色为淡蓝色
      cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
      // 设置填充字体的样式
      cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
      // 设置单元格居中对齐
      cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
      // 设置单元格垂直居中对齐
      cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
      // 创建单元格内容显示不下时自动换行
      cellStyle.setWrapText(true);
      // 设置单元格字体样式
      XSSFFont font = (XSSFFont) workbook.createFont();
      font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 这是字体加粗
      font.setFontName("宋体");// 设置字体的样式
      font.setFontHeight(12);// 设置字体的大小
      cellStyle.setFont(font);// 将字体填充到表格中去
      // 设置单元格边框为细线条（上下左右）
      cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
      return cellStyle;
   }
   // 设置表体的单元格样式
   public CellStyle getBodyStyle(SXSSFWorkbook workbook) {
      // 创建单元格样式
      CellStyle cellStyle = workbook.createCellStyle();
      // 设置单元格居中对齐
      cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
      // 设置单元格居中对齐
      cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
      // 创建单元格内容不显示自动换行
      cellStyle.setWrapText(true);
      // 设置单元格字体样式
      XSSFFont font = (XSSFFont) workbook.createFont();
      font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 这是字体加粗
      font.setFontName("宋体");// 设置字体
      font.setFontHeight(10);// 设置字体的大小
      cellStyle.setFont(font);// 将字体添加到表格中去

      // 设置单元格边框为细线条
      cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
      return cellStyle;
   }
   // 设置表体的单元格样式
   public CellStyle getBodyStyle() {
      // 创建单元格样式
      CellStyle cellStyle = wb.createCellStyle();
      // 设置单元格居中对齐
      cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
      // 设置单元格居中对齐
      cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
      // 创建单元格内容不显示自动换行
      cellStyle.setWrapText(true);
      // 设置单元格字体样式
      XSSFFont font = (XSSFFont) wb.createFont();
      font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 这是字体加粗
      font.setFontName("宋体");// 设置字体
      font.setFontHeight(12);// 设置字体的大小
      cellStyle.setFont(font);// 将字体添加到表格中去
      // 设置单元格边框为细线条
      cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
      cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
      return cellStyle;
   }
   //拼装表头
   public Cell setHeadStyle(String[] titles) {
      Cell cell = null;
      CellStyle headStyle = getHeadStyle();
      Row headRow = sheet.createRow(0);
      // 构建表头
      for (int i = 0; i < titles.length; i++) {
         cell = headRow.createCell(i);
         cell.setCellStyle(headStyle);
         cell.setCellValue(titles[i]);
      }
      return cell;
   }
   public Cell setHeadStyle(String[] titles, SXSSFWorkbook workbook) {
      Cell cell = null;
      CellStyle headStyle = getHeadStyle(workbook);
      Row headRow = sheet.createRow(0);
      // 构建表头
      for (int i = 0; i < titles.length; i++) {
         cell = headRow.createCell(i);
         cell.setCellStyle(headStyle);
         cell.setCellValue(titles[i]);
      }
      return cell;
   }
   /*
   * 列头单元格样式
   */
   public CellStyle getColumnTopStyle(SXSSFWorkbook workbook) {

      // 设置字体
      Font font = workbook.createFont();
      // 设置字体大小
      font.setFontHeightInPoints((short) 18);
      // 字体加粗
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
      // 设置字体名字
      font.setFontName("Courier New");
      // 设置样式;
      CellStyle style = workbook.createCellStyle();
      // 设置底边框;
      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      // 设置底边框颜色;
      style.setBottomBorderColor(HSSFColor.BLACK.index);
      // 设置左边框;
      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
      // 设置左边框颜色;
      style.setLeftBorderColor(HSSFColor.BLACK.index);
      // 设置右边框;
      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
      // 设置右边框颜色;
      style.setRightBorderColor(HSSFColor.BLACK.index);
      // 设置顶边框;
      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
      // 设置顶边框颜色;
      style.setTopBorderColor(HSSFColor.BLACK.index);
      // 在样式用应用设置的字体;
      style.setFont(font);
      // 设置自动换行;
      style.setWrapText(false);
      // 设置水平对齐的样式为居中对齐;
      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      // 设置垂直对齐的样式为居中对齐;
      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
      return style;
   }

   /*
   * 导出数据
   */
   public SXSSFWorkbook export() throws Exception {
      String nsheetName = sheetName;
      Integer rowaccess = 2000;// 内存中缓存记录行数，以免内存溢出
      SXSSFWorkbook workbook = new SXSSFWorkbook(rowaccess);
      try {
         //按sheet页存储，如果存储于一个sheet,去除此处
         int index = 0;
         int sheetNum = (int) Math.ceil(dataList.size()/100000);
         for (int i=1;i<=sheetNum;i++){
            Sheet sheet = workbook.createSheet(nsheetName+"_"+i);
            // 产生表格标题行
            Row titleRow = sheet.createRow(0);
            Cell cellTiltle = titleRow.createCell(0);
            CellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (headers.length - 1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(sheetName);
            Cell cell = null;
            CellStyle headStyle = this.getHeadStyle(workbook);
            // 定义所需列数
            int columnNum = headers.length;
            Row headRow = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)
            //表头
            for (int n = 0; n < columnNum; n++) {
               Cell cellRowName = headRow.createCell(n); // 创建列头对应个数的单元格
               cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
               HSSFRichTextString text = new HSSFRichTextString(headers[n]);
               cellRowName.setCellValue(headers[n]); // 设置列头单元格的值
               cellRowName.setCellStyle(headStyle); // 设置列头单元格样式
            }
            CellStyle bodyStyle = this.getBodyStyle(workbook);
            // 表体数据
            for (int ii = 0; ii < 100000; ii++) {
               Map<String,Object> map = dataList.get(index);
               Row row = sheet.createRow(ii + 3);// 创建所需的行数
               cell = row.createCell(0);
               cell.setCellValue(map.get("id").toString()); // 单元格的值
               cell.setCellStyle(bodyStyle); // 单元格的样式
               cell = row.createCell(1);
               cell.setCellValue(map.get("name").toString()); // 单元格的值
               cell.setCellStyle(bodyStyle); // 单元格的样式
               cell = row.createCell(2);
               cell.setCellValue(map.get("sex").toString()); // 单元格的值
               cell.setCellStyle(bodyStyle); // 单元格的样式
               cell = row.createCell(3);
               cell.setCellValue(map.get("address").toString()); // 单元格的值
               cell.setCellStyle(bodyStyle); // 单元格的样式
               index++;
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return workbook;
   }


}
