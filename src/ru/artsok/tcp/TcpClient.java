package ru.artsok.tcp;


import ru.artsok.Main;
import ru.artsok.util.impl.ParserJaxbImpl;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class TcpClient {
    private InputStream inputStream;
    private OutputStream outputStream;
    public static boolean isALive;
    public static boolean isConnect;
    private String nameTspServer;

    public TcpClient(String nameTspServer) {
        this.nameTspServer = nameTspServer;
    }

    public void start() {
        new Thread(() -> {
            try (Socket socket = new Socket(nameTspServer, 7588)) {
                getFestConnect(socket);
                System.err.println("Соеденение установленно");
                isALive = true;
                isConnect = true;
                response(socket);
            } catch (IOException e) {
                isConnect = false;
                System.err.println("Нет соеденения " + e.getMessage());
                waitingForConnection();
            }
        }).start();

    }

    private void waitingForConnection() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try (Socket socket = new Socket(nameTspServer, 7588)) {
                    getFestConnect(socket);
                    isConnect = true;
                    System.err.println("Соеденение установленно");
                    isALive = true;
                    response(socket);
                    timer.cancel();
                } catch (IOException e) {
                    if (Main.appIsLive) {
                        isConnect = false;
                        System.err.println("Сервер не доступен...\nОжидание... 5 сек. \nException: " + e.getMessage());
                    } else {
                        timer.cancel();
                    }
                }
            }
        }, 5000, 5000);
    }

    private void getFestConnect(Socket socket) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        socket.setSoTimeout(3000);
        bufferedWriter.write(new ParserJaxbImpl().getMiguToString());
        bufferedWriter.flush();
    }


    private void response(Socket socket) {
        String inputText;
        while (isALive && Main.appIsLive) {
            System.out.println("Resp start");

            try {
                socket.setSoTimeout(10000);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "cp1251"));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "cp1251"));
                inputText = bufferedReader.readLine();
                if (inputText.equals("getState")) {
                    System.out.println("getState");
                    bufferedWriter.write(new ParserJaxbImpl().getMiguToString() + "\n");
                    bufferedWriter.flush();
                } else if (inputText.equals("getNumb")) {
                    System.out.println("getNumb");
                    bufferedWriter.write(new ParserJaxbImpl().getMiguToString() + "\n");
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
            System.err.println("Соеденение закрыто");
        } catch (IOException e) {
            System.err.println("Нет соеденения \nException " + e.getMessage());
        } finally {
            isALive = true;
        }
    }
}
