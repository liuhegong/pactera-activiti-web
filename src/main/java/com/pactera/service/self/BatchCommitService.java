package com.pactera.service.self;

import com.pactera.mybatis.dao.UserMapper;
import com.pactera.mybatis.model.User;
import com.pactera.utils.BatchOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pactera on 2017/11/30.
 */
@Service
public class BatchCommitService {

   @Autowired
   UserMapper userMapper;

   public void save(List<User> list) throws Exception {
//      String pack = this.userMapper.getClass().getPackage().getName();
//      String cla = this.userMapper.getClass().getName();
//      String method = "insertBatch";
//      StringBuilder sql = new StringBuilder();
//      sql.append(pack).append(".").append(cla).append(".").append(method);
      String pack = "com.pactera.mybatis.dao.UserMapper.insertBatch";
      BatchOperation.batchCommit(pack,list);
   }
}
