package com.tavi.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.tavi.util.DatabaseUtil;

import model.User;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        try {
            Socket socket = new Socket("localhost", 4444);
            System.out.println("Connected");
            Scanner scanner=new Scanner(System.in);
            DatabaseUtil util=new DatabaseUtil();
            boolean loop=true;
            while(loop){
            	switch(scanner.nextInt()){
            	case 0:{
            		loop=false;
            		break;
            	}
            	case 1:{
            		loginUser(util);
            		break;
            	}
            	case 2:{
            		registerUser(util);
            		break;
            	}
            	}
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void loginUser(DatabaseUtil util) throws Exception{
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Enter your username");
    	String username=sc.nextLine();
    	System.out.println("Enter your password");
    	String password=sc.nextLine();
   		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		util.setup();
		util.startTransaction();
		boolean result=util.loginUser(user);
		util.commitTransaction();
		util.stopEntityManager();
		System.out.println(result);
    }
    
    private static void registerUser(DatabaseUtil util) throws Exception{
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Enter your username");
    	String username=sc.nextLine();
    	System.out.println("Enter your password");
    	String password=sc.nextLine();
    	User user=new User();
    	user.setUsername(username);
    	user.setPassword(password);
    	util.setup();
		util.startTransaction();
		util.saveUser(user);
		util.commitTransaction();
		util.stopEntityManager();
    }
}
