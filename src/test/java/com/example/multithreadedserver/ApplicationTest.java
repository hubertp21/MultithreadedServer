package com.example.multithreadedserver;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationTest {

    private final String LOCALHOST = "127.0.0.1";
    private final int PORT = 7777;
    private Server server;
    private Thread serverThread;

    @BeforeEach
    @SneakyThrows
    void setupServer() {
        server = new Server();
        serverThread = new Thread(() -> server.start(PORT));
        serverThread.start();
        Thread.sleep(1000);
    }

    @AfterEach
    @SneakyThrows
    void shutdownServer() {
        server.stop();
        serverThread.join();
    }

    @Test
    void shouldHandleSingleClient() {
        // given
        Client client = new Client();
        client.startConnection(LOCALHOST, PORT);

        // when
        String response = client.sendMessage("Test message");
        String terminateResponse = client.sendMessage(".");

        // then
        client.closeConnection();
        Assertions.assertEquals("Response: Test message", response);
        Assertions.assertEquals("Connection closed", terminateResponse);
    }

    @Test
    void shouldHandleMultipleClientsAfterEach() {
        // given
        Client clientOne = new Client();
        clientOne.startConnection(LOCALHOST, PORT);

        // when
        String responseOne = clientOne.sendMessage("Test message");
        String terminateResponseOne = clientOne.sendMessage(".");

        // then
        clientOne.closeConnection();
        Assertions.assertEquals("Response: Test message", responseOne);
        Assertions.assertEquals("Connection closed", terminateResponseOne);

        // given
        Client clientTwo = new Client();
        clientTwo.startConnection(LOCALHOST, PORT);

        // when
        String responseTwo = clientTwo.sendMessage("Test message");
        String terminateResponseTwo = clientTwo.sendMessage(".");

        // then
        clientTwo.closeConnection();
        Assertions.assertEquals("Response: Test message", responseTwo);
        Assertions.assertEquals("Connection closed", terminateResponseTwo);
    }

    @Test
    void shouldHandleMultipleClientsAsynchronously() {
        // given
        Client clientOne = new Client();
        clientOne.startConnection(LOCALHOST, PORT);
        Client clientTwo = new Client();
        clientTwo.startConnection(LOCALHOST, PORT);

        // when
        String responseOne = clientOne.sendMessage("Test message");
        String responseTwo = clientTwo.sendMessage("Test message");

        // then
        Assertions.assertEquals("Response: Test message", responseOne);
        Assertions.assertEquals("Response: Test message", responseTwo);

        // when
        String terminateResponseOne = clientOne.sendMessage(".");
        String terminateResponseTwo = clientTwo.sendMessage(".");

        // then
        clientOne.closeConnection();
        clientTwo.closeConnection();
        Assertions.assertEquals("Connection closed", terminateResponseOne);
        Assertions.assertEquals("Connection closed", terminateResponseTwo);
    }
}