# Simple Multithreaded Server

This project is a simple multithreaded server built using Java and Maven. The server creates a separate thread for each client connection, allowing multiple clients to connect and interact with the server concurrently.

## Features

- Multithreaded architecture: Each client connection is handled in a separate thread.
- Simple and clean code structure.
- Built using Maven for easy dependency management and project setup.

## Requirements

- Java 8 or higher
- Maven 3.6.3 or higher

## Getting Started

### Clone the repository

```sh
git clone https://github.com/hubertp21/simple-multithreaded-server.git
cd simple-multithreaded-server
``` 

### Build the project

Use Maven to build the project:

```sh
mvn clean install
```

### Run the server

After building the project, you can run the server using the following command:

```sh
java -jar target/simple-multithreaded-server-1.0-SNAPSHOT.jar
```

### Connect to the server
You can use any client (e.g., telnet, netcat, or a custom client) to connect to the server:

```sh
telnet localhost 7777
```
or
```sh
nc localhost 8080
```

### Project Structure
- src/main/java - Contains the Java source files for the server.
- src/main/resources - Contains the resource files for the server.
- src/test/java - Contains the test files for the server.
- pom.xml - Maven configuration file.

HF.