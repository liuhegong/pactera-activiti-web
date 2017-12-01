package com.pactera.poi;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by pactera on 2017/11/30.
 */
public class ImportUtil {

   //获取工作簿
   public static Workbook getWB(String file){
      Workbook wb = null;
      InputStream inputStream=null;
      try {
         inputStream = new FileInputStream(file);
         wb = WorkbookFactory.create(inputStream);
         //2003版本的excel，用.xls结尾
//         wb = new HSSFWorkbook(fileInputStream);
      }catch (Exception e){
//         try {
//            fileInputStream = new FileInputStream(file);
//            //2007版本的excel，用.xlsx结尾
//            wb = new XSSFWorkbook(fileInputStream);
//         }catch (Exception ee){
//            ee.printStackTrace();
//         }
      }
      return wb;
   }
   //上传获取文件绝对路径
   public static String getPath(HttpServletRequest request){
      String uploadPath = "/excel/upload";
      try {
         long start = System.currentTimeMillis();
         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
         CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
         //检查form中是否有enctype="multipart/form-data"
         if (resolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            while (iter.hasNext()){
               MultipartFile file = multiRequest.getFile(iter.next().toString());
               if (!file.isEmpty()){
                  String fileName = new Date().getTime()+"-"+file.getOriginalFilename();
                  uploadPath = request.getSession().getServletContext().getRealPath(uploadPath);
                  File fpath = new File(uploadPath);
                  if (!fpath.exists()){
                     fpath.mkdirs();
                  }
                  uploadPath = uploadPath+"/"+fileName;
                  File fp = new File(uploadPath);
                  file.transferTo(fp);
               }
            }
         }
         long end = System.currentTimeMillis();
         System.out.println("上传共耗时："+(end-start)/1000+"秒");
      }catch (Exception e){
         e.printStackTrace();
      }
      return uploadPath;
   }
}
