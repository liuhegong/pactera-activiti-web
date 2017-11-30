import com.pactera.mybatis.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pactera on 2017/11/30.
 */
public class Test {
   public static void main(String[] args) {
      Test test = new Test();
      List<User> list = test.getUsers();
      List<User> tempStudents = new ArrayList<User>();
      long start = System.currentTimeMillis();
      for (int i = 0; i < list.size(); i++) {
         tempStudents.add(list.get(i));
         if (i !=0 && i%50000 == 0){
            test.createThread(tempStudents,i);
         }
         test.createThread(tempStudents,i);
      }
      long end = System.currentTimeMillis();
      System.out.println(end-start);
   }

   private void createThread(List<User> tempStudents, int i) {
      List<User> users = new ArrayList<User>();
      for (User student : tempStudents) {
         users.add(student);
      }

      Task1 studentThread = new Task1(users);
      // 设置线程名称
      studentThread.setName("Thread-" + i);
      // 启动线程
      studentThread.start();
      tempStudents = new ArrayList<User>();
   }

   public List<User> getUsers() {
      List<User> students = new ArrayList<User>();
      User student = null;
      for (int i = 1; i <= 100000; i++) {
         student = new User();
         student.setName("wjwei" + i);
         student.setSex(i + "");
         students.add(student);
      }
      return students;
   }

   static class Task1 extends Thread{
      List<User> list = new ArrayList<>();
      public Task1(List<User> list){
         this.list=list;
      }
      @Override
      public void run() {
         System.out.println(""+Thread.currentThread().getName());
         for (User user : list) {
            System.out.println("name = " + user.getName() +  " sex = " + user.getSex());
         }
      }
   }
}
