package cn.itcast.b_reflect;

import org.junit.Test;

public class App {

	@Test
	public void testSave() throws Exception {
		
		AdminDao adminDao = new AdminDao();
		Admin admin = adminDao.findById(8);
		System.out.println(admin);
		
		System.out.println(adminDao.getAll());
	}
}
