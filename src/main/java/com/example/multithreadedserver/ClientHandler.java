package com.example.multithreadedserver;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        createStreams();
        handleRequest();
        closeConnection();
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

    private void handleRequest() {
        log.info("Handling client: {} request",
                clientSocket.getInetAddress().getHostAddress());
        String inputLine;
        try {
            while ((inputLine = inputStream.readLine()) != null) {
                if (inputLine.equals(".")) {
                    outputStream.println("Connection closed");
                    break;
                }
                String response = createResponse(inputLine);
                log.info("{}", response);
                outputStream.println(response);
            }
        } catch (IOException ioException) {
            log.error("Error when handling request: {}",
                    ioException.getMessage());
        }
    }

    private String createResponse(String inputLine) {
        return String.format("Response: %s", inputLine);
    }

    private void closeConnection() {
        try {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException ioException) {
            log.error("Error when closing connection: {}",
                    ioException.getMessage());
        }
    }
}
