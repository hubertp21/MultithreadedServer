package com.example.multithreadedserver;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class Client {

    private Socket clientSocket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;

    public void startConnection(String ip, int port) {
        createSocket(ip, port);
        createStreams();
    }

    public void closeConnection() {
        try {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            log.info("Connection closed");
        } catch (IOException ioException) {
            log.error("Error when closing connection: {}",
                    ioException.getMessage());
        }
    }

    public String sendMessage(String msg) {
        outputStream.println(msg);
        return getResponse();
    }

    private String getResponse() {
        String response = "";
        try {
            response = inputStream.readLine();
        } catch (IOException ioException) {
            log.error("Error when getting response: {}",
                    ioException.getMessage());
        }
        return response;
    }

    private void createSocket(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
        } catch (IOException ioException) {
            log.error("Error in creating socket: {}",
                    ioException.getMessage());
        }
    }

    private void createStreams() {
        try {
            outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ioException) {
            log.error("Error in creating stream: {}",
                    ioException.getMessage());
        }
    }
}
