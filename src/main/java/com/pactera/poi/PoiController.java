package com.pactera.poi;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pactera on 2017/11/29.
 */
@Controller
@RequestMapping("/poi")
public class PoiController {

   @RequestMapping("/export")
   public void export(HttpServletResponse response) throws Exception {
      String[] titles = {"ID","姓名","性别","地址"};
      String name = "测试大批量导出报表";
      String title = new String(name.getBytes("gb2312"), "ISO8859-1");
      String sheetName = new String(name.getBytes(), "UTF-8");
      OutputStream outputStream = response.getOutputStream();
      response.reset();
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/x-msdownload");
      response.setHeader("Content-disposition", "attachment; filename=" +title + ".xlsx");
      List<Map<String,Object>> dataList = getListData(titles);
      ExportUtil exportUtil = new ExportUtil(sheetName,titles,dataList);
      //SXSSFWorkbook 大批量数据导出
      long start = System.currentTimeMillis();
      SXSSFWorkbook workBook = exportUtil.export();
      workBook.write(outputStream);
      long end = System.currentTimeMillis();
      System.out.println("写入excel耗时："+(end-start)/1000+"秒");
      outputStream.flush();
      outputStream.close();
   }
   /**
    * 封装数据
    * 将需要导出的数据封装成一个List<Object[]>的集合
    * String[] headers是导出excel的头部
    */
   private List<Map<String,Object>> getListData(String[] headers){
      long start = System.currentTimeMillis();
      List<Map<String,Object>> dataList = getData();
      long end = System.currentTimeMillis();
      System.out.println("-----组装数据耗时: "+(end-start)/1000+"秒");
      return dataList;
   }

   private List<Map<String,Object>> getData() {
      List<Map<String,Object>> list = new ArrayList<>();
      Map<String,Object> map = null;
      for (int i=1;i<=500000;i++) {
         map = new HashMap<>();
         map.put("id",i);
         map.put("name","王建伟"+i);
         map.put("sex","男");
         map.put("address","北京市昌平区中西医结合医院家属楼");
         list.add(map);
      }
      return list;
   }
}
