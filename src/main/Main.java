package main;

import model.User;

import com.tavi.util.DatabaseUtil;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DatabaseUtil dbutil=new DatabaseUtil();
		User user=new User();
		user.setIdusers(2);
		user.setUsername("yrd");
		user.setPassword("test");
		dbutil.setup();
		dbutil.startTransaction();
		dbutil.saveUser(user);
		dbutil.commitTransaction();
		dbutil.getAllUsers();
		dbutil.stopEntityManager();
	}

}
