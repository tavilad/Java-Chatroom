package com.tavi.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {
    private Socket clientSocket;
    private Server server;

    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientConnectionHandler(Socket socket, Server server) throws IOException {
        this.clientSocket = socket;
        this.server = server;
        this.inputStream = this.clientSocket.getInputStream();
        this.outputStream = this.clientSocket.getOutputStream();
    }

    @Override
    public void run() {

    }
}
