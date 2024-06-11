package com.example.multithreadedserver;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

@Slf4j
public class Server {

    private ServerSocket serverSocket;
    private volatile boolean running = false;

    public void start(int port) {
        createServerSocket(port);
        running = true;
        while (running) {
            handleClient();
        }
    }

    public void stop() {
        running = false;
        try {
            log.info("Stopping server");
            serverSocket.close();
        } catch (IOException ioException) {
            log.error("Exception caught during server stopping: {}",
                    ioException.getMessage());
        }
    }

    private void handleClient() {
        try {
            new ClientHandler(serverSocket.accept()).start();
        } catch (SocketException socketException) {
            handleSocketException(socketException);
        } catch (IOException ioException) {
            log.error("Error when accepting client connection: {}",
                    ioException.getMessage());
        }
    }

    private void handleSocketException(SocketException socketException) {
        if (!running) {
            log.info("Server socket closed, stopping accept loop.");
        } else {
            log.error("SocketException when accepting client connection: {}",
                    socketException.getMessage());
        }
    }

    private void createServerSocket(int port) {
        try {
            serverSocket = new ServerSocket(port);
            log.info("Server socket created on port: {}", port);
        } catch (IOException ioException) {
            log.error("Error in creating server socket: {}",
                    ioException.getMessage());
        }
    }
}
