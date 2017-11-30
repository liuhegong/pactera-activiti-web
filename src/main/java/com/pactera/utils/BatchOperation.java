package com.pactera.utils;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pactera on 2017/11/30.
 */
public class BatchOperation {
   private static int count = 2000;//每次允许提交多少条数据

   public static <T> void batchCommit(SqlSessionFactory sqlSessionFactory,String sql,List<T> list){
      SqlSession session = null;
      try {
         session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
         int commitCount = (int) Math.ceil(list.size()/count);
         List<T> tempList = new ArrayList<T>(count);
         int start, stop;
         Long startTime = System.currentTimeMillis();
         for (int i = 0; i < commitCount; i++) {
            start = i*count;
            stop = Math.min(i*count+count-1, list.size());
            for (int j = start; j < stop; j++) {
               tempList.add(list.get(j));
            }
//            session.insert();
            session.commit();
            session.clearCache();
         }
         long end = System.currentTimeMillis();
         System.out.println("大批量插入数据耗时："+(end-startTime)/1000+"秒");
      }catch (Exception e){
         session.rollback();
      }finally {
         if (session != null){
            session.close();
         }
      }
   }


}
