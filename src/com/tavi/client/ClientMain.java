package com.tavi.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import com.tavi.controllers.MainController;
import com.tavi.util.DatabaseUtil;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.User;

public class ClientMain {

	private static User loggedUser;
	
	public Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	public MainController mainController;
	public boolean isLoggedIn;
	public ReadHandler readHandler;
	
	public void connect() {
		try {
			this.socket = new Socket("localhost", 4444);
			this.outputStream = new ObjectOutputStream(
					this.socket.getOutputStream());
			this.inputStream=new ObjectInputStream(this.socket.getInputStream());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void login(User user) {
		try {
			this.outputStream.writeObject(1);
			this.outputStream.writeObject(user);
			if((boolean)this.inputStream.readObject()) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Logged in succesfully", ButtonType.OK);
				alert.showAndWait();
				this.isLoggedIn=true;
				mainController.loginButton.setDisable(true);
				mainController.registerButton.setDisable(true);
				mainController.disconnectButton.setDisable(false);
				this.readHandler=new ReadHandler(socket, user,this,this.inputStream);
				this.readHandler.start();
			}else {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Wrong credentials", ButtonType.OK);
				alert.showAndWait();
				this.isLoggedIn=false;
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void register(User user) {
		try {
			this.outputStream.writeObject(2);
			this.outputStream.writeObject(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void sendString(String message) {
		try {
			outputStream.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private static boolean loginUser(DatabaseUtil util) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your username");
		String username = sc.nextLine();
		System.out.println("Enter your password");
		String password = sc.nextLine();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		util.setup();
		util.startTransaction();
		boolean result = util.loginUser(user);
		util.commitTransaction();
		util.stopEntityManager();
		System.out.println(result);
		loggedUser = user;
		return result;
	}

	private static void registerUser(DatabaseUtil util) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your username");
		String username = sc.nextLine();
		System.out.println("Enter your password");
		String password = sc.nextLine();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		util.setup();
		util.startTransaction();
		util.saveUser(user);
		util.commitTransaction();
		util.stopEntityManager();
	}
}
