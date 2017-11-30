package com.pactera.poi;

import com.alibaba.fastjson.JSON;
import com.pactera.mybatis.dao.UserMapper;
import com.pactera.mybatis.model.User;
import com.pactera.utils.BatchOperation;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

   @Autowired
   private UserMapper userMapper;
   @Autowired
   private SqlSessionFactory sqlSessionFactory;

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
         list.add(map);
      }
      return list;
   }
   /**
    * 导入
    */
   @ResponseBody
   @RequestMapping("/import")
   public String importExcel(HttpServletRequest request){
      List<User> list = new ArrayList<>();
      Map<String,Object> map = new HashMap<>();
      try {
         String path = ImportUtil.getPath(request);
         Workbook book = ImportUtil.getWB(path);
         list = getDataList(book);
         saveData(sqlSessionFactory,list);
         map.put("status","success");
         map.put("message","导入成功");
      }catch (Exception e){
         map.put("status","failure");
         map.put("message","导入失败");
      }
      return JSON.toJSONString(map);
   }

   private void saveData(SqlSessionFactory sqlSessionFactory, List<User> list) {
      String pack = userMapper.getClass().getPackage().getName();
      String cla = userMapper.getClass().getName();
      String method = "insertBatch";
      StringBuilder sql = new StringBuilder();
      sql.append(pack).append(".").append(cla).append(".").append(method);
      BatchOperation.batchCommit(sqlSessionFactory,sql.toString(),list);
   }

   private List<User> getDataList(Workbook book) {
      List<User> list = new ArrayList<>();
      Sheet sheet = book.getSheetAt(0);
      for (int i = 1; i <= sheet.getLastRowNum(); i++) {
         Row row = sheet.getRow(i);
         if (row == null){
            continue;
         }
         //此处可以判断列的类型....
         //对列数据进行验证....
         String name = row.getCell(2).getStringCellValue();
         String sex = row.getCell(3).getStringCellValue();
         User user = new User();
         user.setName(name);
         user.setSex(sex);
         list.add(user);
      }
      return list;
   }
}
