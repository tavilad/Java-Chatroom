package com.tavi.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import com.tavi.client.WriteHandler;

import model.User;

public class ClientConnectionHandler extends Thread {
    private Socket clientSocket;
    private Server server;

    private ObjectInputStream inputStream;
    public ObjectOutputStream outputStream;
    private User user;

    public ClientConnectionHandler(Socket socket, Server server,User user,ObjectInputStream inputStream,ObjectOutputStream outputStream) throws IOException {
        this.clientSocket = socket;
        this.server = server;
        this.user=user;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
    
    public void run() {
        try {
            String clientMessage;
            String messageToSend;
            do {
                clientMessage = (String) inputStream.readObject();
                messageToSend = "[" + this.user.getUsername() + "]: " + clientMessage;
                System.out.println(messageToSend);
                server.broadcast(messageToSend);
 
            } while (!clientMessage.equals("bye"));
 
            server.removeUser(this.user, this);
            this.clientSocket.close();
 
            messageToSend = user.getUsername() + " has quitted.";
            server.broadcast(messageToSend);
 
        } catch (IOException ex) {
            ex.printStackTrace();
            server.removeUser(this.user, this);
            server.broadcast(user.getUsername() + " left the chatroom.");
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    void sendMessage(String message) {
        try {
			this.outputStream.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
