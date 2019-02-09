package com.tavi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private Socket socket=null;
    private ServerSocket serverSocket=null;
    private int port;

    public Server(int port){
        try{
            this.serverSocket=new ServerSocket(port);
            System.out.println("Server started on port "+port);
            System.out.println("Waiting for client connection");
            while(true){
                this.socket=this.serverSocket.accept();
                System.out.println("Received connection from" +this.socket.getInetAddress().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
