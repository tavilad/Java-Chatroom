package com.tavi.client;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import model.User;

public class WriteHandler extends Thread {
	private Socket socket;
	private ObjectOutputStream outputStream;
	private User loggedUser;
	static Logger log = Logger.getLogger(WriteHandler.class.getName());
	public boolean shouldLoop=true;

	public WriteHandler(Socket socket, User user) {
    	BasicConfigurator.configure();
		this.socket = socket;
		this.loggedUser = user;
		try {
			this.outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		Scanner sc=new Scanner(System.in);
		String text;
		do {
			text = sc.nextLine();
			try {
				outputStream.writeObject(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!text.equals("bye"));
		this.shouldLoop=false;
	}
}
