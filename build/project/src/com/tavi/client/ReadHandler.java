package com.tavi.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.tavi.controllers.MainController;

import model.User;

public class ReadHandler extends Thread {
	private Socket socket;
	private ObjectInputStream inputStream;
	private User loggedUser;
	private ClientMain clientMain;

	public ReadHandler(Socket socket, User user, ClientMain clientMain, ObjectInputStream inputStream) {
		this.socket = socket;
		this.loggedUser = user;
		this.clientMain = clientMain;
		System.out.println("Read handler constructor");

		this.inputStream = inputStream;

	}

	public void run() {
		while (true) {
			try {
				String response = (String) inputStream.readObject();
				this.clientMain.mainController.recieveText(response);
				System.out.println("\n" + response);
			} catch (IOException ex) {
				System.out.println("Error reading from server: " + ex.getMessage());
				ex.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

}
