package main;

import model.User;

import com.tavi.util.DatabaseUtil;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DatabaseUtil dbutil=new DatabaseUtil();
		User user=new User();
		user.setIdusers(1);
		user.setUsername("tavilad");
		user.setPassword("anamaria");
		dbutil.setup();
		dbutil.startTransaction();
		dbutil.saveUser(user);
		dbutil.commitTransaction();
		dbutil.getAllUsers();
		dbutil.stopEntityManager();
	}

}
