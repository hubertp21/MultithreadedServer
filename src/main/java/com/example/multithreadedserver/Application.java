package com.example.multithreadedserver;

public class Application {

    public static void main(String[] args) {
        Server server = new Server();
        server.start(7777);
    }
}
