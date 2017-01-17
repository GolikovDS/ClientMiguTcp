package ru.artsok.SerialPort;


import javafx.scene.control.TextArea;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
import ru.artsok.entity.Migu;
import ru.artsok.entity.MiguState;
import ru.artsok.entity.interfaces.MiguHandle;

import java.util.*;


public class SerialPortMigu {
    private static SerialPort serialPort;
    private volatile static SerialPortMigu portMigu;


    private SerialPortMigu() {
    }

    public static SerialPortMigu getPort(String port, int speed, int bitData, String party, String stopBit) throws SerialPortException {
        if (portMigu == null) {
            synchronized (SerialPortMigu.class) {
                serialPort = new SerialPort(port);
                serialPort.openPort();
                serialPort.setParams(speed, bitData, Integer.parseInt(stopBit), SerialPort.PARITY_NONE);
                portMigu = new SerialPortMigu();
            }
        }
        return portMigu;
    }

    /////////////////////////////////////////////////////////////////
    //callback
    /////////////////////////////////////////////////////////////////
    public interface UpdateDataCallBack {
        void calling(byte[] bytes);

        void callIsFreshMigu();
    }

    UpdateDataCallBack callBack;

    public void registerCallBack(UpdateDataCallBack callBack) {
        this.callBack = callBack;
    }


    public synchronized byte[] getData(List<Byte> request, int sizeResponse, TextArea terminal) throws SerialPortException, SerialPortTimeoutException {
        byte[] response = null;
        terminalOutput(request, terminal);
        CRC16 crc16 = new CRC16(request);
        serialPort.writeBytes(crc16.getDataAndCrc());
        response = serialPort.readBytes(sizeResponse, 500);
        terminalInput(response, terminal);
        return response;
    }

    public synchronized byte[] getData(List<Byte> request, int sizeResponse) throws SerialPortException, SerialPortTimeoutException {
        CRC16 crc16 = new CRC16(request);
        serialPort.writeBytes(crc16.getDataAndCrc());
        return serialPort.readBytes(sizeResponse, 500);
    }

    public void terminalOutput(List<Byte> bytes, TextArea serialTerminalScreen) {
        StringBuilder str = new StringBuilder("<< ");
        for (byte b : bytes)
            str.append(String.format("0x%02X ", b));
        serialTerminalScreen.appendText(str + "\n");
    }

    public void terminalInput(byte[] bytes, TextArea serialTerminalScreen) {
        StringBuilder str = new StringBuilder(">> ");
        for (byte b : bytes)
            str.append(String.format("0x%02X ", b));
        serialTerminalScreen.appendText(str + "\n");
    }

    public void readAllRegMigu2() {
        new Thread(() -> {
            while (serialPort.isOpened()) {
                MiguHandle.miguMap.getMap().get(1).setStates(new MiguState(true));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void readAllRegMigu(UpdateDataCallBack callBack) {
        Thread thread = new Thread(() ->
        {
            registerCallBack(callBack);
            long timeRequest = 0;
            int counterRequestForDeathMigu = 0;

            for (Migu migu : MiguHandle.miguMap.getMap().values())
                migu.getStates().setMiguIsRespond(true);

            while (serialPort.isOpened()) {
                timeRequest = System.currentTimeMillis();
                for (Migu migu : MiguHandle.miguMap.getMap().values()) {
                    if (migu.getStates().isMiguIsRespond()) {
                        try {
                            callBack.calling(portMigu.getData(Arrays.asList(new Byte[]{(byte) migu.getAddress(), 0x41, 0x00, 0x00}), 7));
                            migu.getStates().setMiguIsRespond(true);
                        } catch (SerialPortTimeoutException | SerialPortException e) {
                            System.err.println("Exception !!!" + e.getMessage());
                            migu.getStates().setMiguIsRespond(false);
                        }
                    }
                }
                //проверка раз в 5 секунд что МИЖУ не отвечает
                if (counterRequestForDeathMigu == 5) {
                    counterRequestForDeathMigu = 0;
                    for (Migu deathMiguAddres : MiguHandle.miguMap.getMap().values()) {
                        try {
                            portMigu.getData(Arrays.asList(new Byte[]{(byte) deathMiguAddres.getAddress(), 0x41, 0x00, 0x00}), 7);
                            deathMiguAddres.getStates().setMiguIsRespond(true);
                        } catch (SerialPortException | SerialPortTimeoutException e) {
//                        serialTerminalScreen.appendText("Нет ответа " + e.getMessage() + "\n");
                            deathMiguAddres.getStates().setMiguIsRespond(false);
                            System.err.println("Нет ответа от МИЖУ №" + deathMiguAddres + " \nОшибка: " + e.getMessage());
                        }
                    }
                }
                counterRequestForDeathMigu++;
                timeRequest -= System.currentTimeMillis();
                //опрос не более 1 раза в секунду -  рассчитываеться время потраченное на запрос/ответ от МИЖУ и
                // отнимаеться от секунды, остаток от секунды ждем.
                System.out.println("Time request" + timeRequest);
                if (timeRequest < 1000)
                    try {
                        Thread.sleep(1000 - timeRequest);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                callBack.callIsFreshMigu();
            }

        });
        thread.setDaemon(true);
        thread.start();
    }


    public void closeConnection() throws SerialPortException {
        portMigu = null;
        if (serialPort != null)
            if (serialPort.isOpened())
                serialPort.closePort();
    }
}
