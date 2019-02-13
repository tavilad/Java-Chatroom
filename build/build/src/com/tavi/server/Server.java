package com.tavi.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.tavi.util.DatabaseUtil;

import model.User;

public class Server {
	private Socket socket = null;
	private ServerSocket serverSocket = null;
	private int port;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private ArrayList<User> users;
	private ArrayList<ClientConnectionHandler> connectionHandlers;
	private ArrayList<String> userNames;
	static Logger log = Logger.getLogger(Server.class.getName());
	private User user;

	public Server(int port) throws Exception {
		BasicConfigurator.configure();
		DatabaseUtil util = new DatabaseUtil();
		this.users = new ArrayList<User>();
		this.connectionHandlers = new ArrayList<ClientConnectionHandler>();
		try {
			this.serverSocket = new ServerSocket(port);
			System.out.println("Server started on port " + port);
			System.out.println("Waiting for client connection");
			while (true) {
				try {
					this.socket = this.serverSocket.accept();
					this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
					this.inputStream = new ObjectInputStream(this.socket.getInputStream());
					System.out.println("Received connection from" + this.socket.getInetAddress().toString());
					switch ((int) this.inputStream.readObject()) {
					case 1:
						System.out.println("Login");
						user=(User) this.inputStream.readObject();
						if(loginUser(util,user)) {
							this.users.add(user);
							System.out.println("Received connection from" + this.socket.getInetAddress().toString() + " "
									+ user.getUsername());
							ClientConnectionHandler handler = new ClientConnectionHandler(this.socket, this, user,
									this.inputStream, this.outputStream);
							this.connectionHandlers.add(handler);
							handler.start();
							this.outputStream.writeObject(true);
							this.userNames=new ArrayList<String>();
							for(User user:this.users) {
								userNames.add(user.getUsername());
							}
							this.outputStream.writeObject(userNames);
							
						}else {
							this.outputStream.writeObject(false);
						}						
						break;
					case 2:
						System.out.println("register");
						user=(User) this.inputStream.readObject();
						registerUser(util,user);
						ClientConnectionHandler handler = new ClientConnectionHandler(this.socket, this, user,
								this.inputStream, this.outputStream);
						this.connectionHandlers.add(handler);
						handler.start();
						break;
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void removeUser(User user, ClientConnectionHandler handler) {
		this.users.remove(user);
		this.connectionHandlers.remove(handler);
		this.userNames.remove(user.getUsername());
		System.out.println("Removed user " + user.getUsername());
	}

	void broadcast(String message) {
		for (ClientConnectionHandler aUser : this.connectionHandlers) {
			aUser.sendMessage(message);
		}
	}

	private static boolean loginUser(DatabaseUtil util,User user) throws Exception {
		util.setup();
		util.startTransaction();
		boolean result = util.loginUser(user);
		util.commitTransaction();
		util.stopEntityManager();
		System.out.println(result);
		return result;
	}

	private static void registerUser(DatabaseUtil util,User user) throws Exception {
		util.setup();
		util.startTransaction();
		util.saveUser(user);
		util.commitTransaction();
		util.stopEntityManager();
	}
}
