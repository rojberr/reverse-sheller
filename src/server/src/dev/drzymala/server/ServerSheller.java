package dev.drzymala.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSheller {

    int port;

    public ServerSheller(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {

        System.out.println();
        System.out.print(" _                                    \n");
        System.out.print("| |                                   \n");
        System.out.print("| |__   __ ___  _____  ___ _ ____   __\n");
        System.out.print("| '_ \\ / _` \\ \\/ / __|/ _ \\ '__\\ \\ / /\n");
        System.out.print("| | | | (_| |>  <\\__ \\  __/ |   \\ V / \n");
        System.out.print("|_| |_|\\__,_/_/\\_\\___/\\___|_|    \\_/  \n");
        System.out.print("                                      \n");
        System.out.println();

        String envPort = System.getenv("SERVER_PORT");
        if (!envPort.isEmpty()) {
            ServerSheller envServerInstance = new ServerSheller(Integer.parseInt(envPort));
            do {
                try {
                    envServerInstance.listen();
                } catch (IOException ioException) {
                    System.out.println("IN/OUT EXCEPTION");
                } catch (InterruptedException interruptedException) {
                    System.out.print("INTERRUPTED");
                }
            } while (true);
        }

        if (args.length != 1) {
            System.out.print("Usage: java -jar server-sheller-0.0.1.jar <port>\n");
        } else {
            boolean error = false;
            int port = -1;
            args[0] = args[0].trim();
            if (args[0].length() < 1) {
                error = true;
                System.out.print("<port> is required\n");
            } else {
                try {
                    port = Integer.parseInt(args[0]);
                    if (port < 0 || port > 65535) {
                        error = true;
                        System.out.print("<port> must be between 0 - 65535\n");
                    }
                } catch (NumberFormatException ex) {
                    error = true;
                    System.out.print("<port> is not valid\n");
                }
            }
            if (!error) {
                ServerSheller serverInstance = new ServerSheller(port);
                do {
                    try {
                        serverInstance.listen();
                    } catch (IOException ioException) {
                        System.out.println("IN/OUT EXEPTION");
                    } catch (InterruptedException interruptedException) {
                        System.out.print("INTERRUPTED");
                    }
                } while (true);
            }
        }
    }

    private void listen() throws IOException, InterruptedException {
        System.err.println("Listening at port " + this.port);
        ServerSocket serverSocket = new ServerSocket(this.port);
        Socket socket = serverSocket.accept();
        System.err.println("Accepted");
        transferStreams(socket);
    }

    private static void transferStreams(Socket socket) throws IOException, InterruptedException {
        InputStream systemInput = System.in;
        OutputStream socketOutput = socket.getOutputStream();
        InputStream socketInput = socket.getInputStream();
        PrintStream output2 = System.out;
        Thread thread1 = new Thread(new StreamTransferer(systemInput, socketOutput));
        Thread thread2 = new Thread(new StreamTransferer(socketInput, output2));
        thread1.start();
        thread2.start();
        thread1.join();
        socket.shutdownOutput();
        thread2.join();
    }
}