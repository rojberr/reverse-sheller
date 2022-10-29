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

        do {
            listen(4444);
        } while (true);
    }

    private static void listen(int port) throws Exception {
        System.err.println("Listening at port " + port);
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        System.err.println("Accepted");
        transferStreams(socket);
    }

    private static void transferStreams(Socket socket) throws IOException,
            InterruptedException {
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