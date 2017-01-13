package ru.artsok.tcp;


import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TcpClient {
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean isALive;
    //    private Socket socket;
    private String nameTspServer;

    public TcpClient(String nameTspServer) {
        this.nameTspServer = nameTspServer;
    }

    public void start() {
        try (Socket socket = new Socket(nameTspServer, 3333)) {

            getFestConnect(socket);
            System.out.println("Соеденение установленно");
            isALive = true;
            response(socket);
        } catch (IOException e) {
            System.err.println("Нет соеденения " + e.getMessage());
            waitingForConnection();
        }

    }

    private void waitingForConnection() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try (Socket socket = new Socket(nameTspServer, 3333)) {
                    getFestConnect(socket);
                    System.out.println("Соеденение установленно");
                    isALive = true;
                    response(socket);
                    timer.cancel();
                } catch (IOException e) {
                    System.err.println("Сервер не доступен...\nОжидание... 5 сек. \n Exception: " + e.getMessage());
                }
            }
        }, 5000, 5000);
    }

    private void getFestConnect(Socket socket) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socket.setSoTimeout(3000);
        bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><MIGU>123<MIGU/><ImIsLive!>\n");
        bufferedWriter.flush();
    }

    private void response(Socket socket) {
        boolean equalsRequest;
        while (isALive) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                equalsRequest = bufferedReader.readLine().equals("getState");
                if (equalsRequest) {
                    bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><MIGU>123<MIGU/><Response>\n");
                    bufferedWriter.flush();
                }

            } catch (IOException e) {
                System.out.println(Thread.currentThread().getName() + " Разрыв соеденения! \nException " + e.getMessage());
                waitingForConnection();
                isALive = false;
            }
        }
    }

    public void stop() {
        try (Socket socket = new Socket(nameTspServer, 3333)) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            socket.setSoTimeout(3000);
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><MIGU>123<MIGU/><ImDying!>");
            bufferedWriter.flush();
            System.out.println("Соеденение закрыто");
        } catch (IOException e) {
            System.out.println("Нет соеденения \nException " + e.getMessage());
        } finally {
            isALive = true;
        }
    }
}
