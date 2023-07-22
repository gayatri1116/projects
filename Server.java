import java.net.*;
import java.io.*;
import java.util.*;

class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting.....");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startReading() {
        // thread read kareke dega
        Runnable r1 = () -> {
            System.out.println("reader started....");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("client terminated the chat");
                        break;
                    }
                    System.out.println("client : " + msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        new Thread(r1).start();

    }

    public void startWriting() {
        System.out.println("Writer started...");

        Runnable r2 = () -> {
            System.out.println("writer started...");

            while (true) {
                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };
        new Thread(r2).start();
        // thread data user lega and send karega client tak
    }

    public static void main(String args[]) {
        System.out.println("this is server ..going to start server");
        new Server();
    }
}
