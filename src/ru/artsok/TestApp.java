package ru.artsok;


import ru.artsok.tcp.TcpClient;

public class TestApp {
    public static void main(String[] args) {
       TcpClient tcpClient = new TcpClient("192.168.55.145");
        tcpClient.start();
        tcpClient.stop();

    }
}
