package com.pactera.poi.importExcel;

import com.pactera.mybatis.model.User;
import com.pactera.utils.xls.XxlsAbstract;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class XxlsPrint extends XxlsAbstract {

	private List<User> list = new ArrayList<>();
	static long end;
	@Override
	public void optRows(int sheetIndex,int curRow, List<String> rowlist) throws SQLException {
		if (curRow>0){
			User user = new User();
			user.setName(rowlist.get(1));
			user.setSex(rowlist.get(2));
			list.add(user);
		}
//		for (int i = 0; i < rowlist.size(); i++) {
//			System.out.print("'" + rowlist.get(i) + "',");
//		}
//		System.out.println();
//		end = System.currentTimeMillis();
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		System.err.println("开始.......");
		XxlsPrint howto = new XxlsPrint();
		howto.processOneSheet("C:\\Users\\Administrator\\Desktop\\测试大批量导出报表.xlsx",1);
//		howto.process("C:\\Users\\Administrator\\Desktop\\测试大批量导出报表.xlsx");
		System.err.println((end-start)/1000);
		System.err.println("结束.......");
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}
}
