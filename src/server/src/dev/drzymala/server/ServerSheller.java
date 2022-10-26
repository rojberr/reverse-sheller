package dev.drzymala.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSheller {

    public static void main(String[] args) throws IOException {

        System.out.println();
        System.out.print(" _                                    \n");
        System.out.print("| |                                   \n");
        System.out.print("| |__   __ ___  _____  ___ _ ____   __\n");
        System.out.print("| '_ \\ / _` \\ \\/ / __|/ _ \\ '__\\ \\ / /\n");
        System.out.print("| | | | (_| |>  <\\__ \\  __/ |   \\ V / \n");
        System.out.print("|_| |_|\\__,_/_/\\_\\___/\\___|_|    \\_/  \n");
        System.out.print("                                      \n");
        System.out.println();

        listen();
    }

    private static void listen() throws IOException {

        ServerSocket serverSocket = new ServerSocket(4444);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (".".equals(inputLine)) {
                out.println("good bye");
                break;
            }
            out.println(inputLine);
        }
    }
}