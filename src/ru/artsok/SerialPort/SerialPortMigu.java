package ru.artsok.SerialPort;


import javafx.scene.control.TextArea;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
import ru.artsok.entity.Migu;
import ru.artsok.entity.MiguState;
import ru.artsok.entity.interfaces.MiguHandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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


//
//    public SerialPortMiguImpl(String port, int speed, int bitData, String party, String stopBit) {
//        synchronized (this) {
//            if (serialPort == null) {
//                try {
//                    serialPort = new SerialPort(port);
//                    serialPort.setParams(speed, bitData, Integer.parseInt(stopBit), 3);
//                } catch (SerialPortException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    public synchronized byte[] getData(List<Byte> request, int sizeResponse, TextArea terminal) throws SerialPortException, SerialPortTimeoutException {
        byte[] response = null;
        terminalOutput(request, terminal);
        CRC16 crc16 = new CRC16(request);
        serialPort.writeBytes(crc16.getDataAndCrc());
        response = serialPort.readBytes(sizeResponse, 1000);
        terminalInput(response, terminal);
        return response;
    }

    public synchronized byte[] getData(List<Byte> request, int sizeResponse) throws SerialPortException, SerialPortTimeoutException {
        CRC16 crc16 = new CRC16(request);
        serialPort.writeBytes(crc16.getDataAndCrc());
        return serialPort.readBytes(sizeResponse, 1000);
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
            //controller.serialTerminalScreen.appendText("Поток: " + Thread.currentThread().getName() + " стартовал\n");
            long timeRequest = 0;
            int counterRequestForDeathMigu = 0;
            byte address = 0;
            List<Byte> deathMigu = new ArrayList<Byte>();

            while (serialPort.isOpened()) {

                timeRequest = System.currentTimeMillis();
                for (Migu migu : MiguHandle.miguMap.getMap().values()) {
                    address = (byte) migu.getAddress();
                    try {
                        if (!deathMigu.contains(address)) {
                            callBack.calling(portMigu.getData(Arrays.asList(new Byte[]{address, 0x03, 0x01, -128, 0x00, 0x00}), 7));
                            MiguHandle.miguMap.setStateByAddress(address, new MiguState(true));
                        }

                    } catch (SerialPortException | SerialPortTimeoutException e) {
//                        serialTerminalScreen.appendText("Нет ответа " + e.getMessage() + "\n");
                        deathMigu.add(address);
                    }
                }
                //проверка раз в 5 секунд что МИЖУ не отвечает
                if (counterRequestForDeathMigu == 5 && deathMigu.size() > 0) {
                    counterRequestForDeathMigu = 0;
                    try {
                        portMigu.getData(Arrays.asList(new Byte[]{address, 0x03, 0x01, -128, 0x00, 0x00}), 7);
                    } catch (SerialPortException | SerialPortTimeoutException e) {
//                        serialTerminalScreen.appendText("Нет ответа " + e.getMessage() + "\n");
                        deathMigu.add(address);
                        MiguHandle.miguMap.setStateByAddress(address, new MiguState(false));
                        System.err.println("Нет ответа " + e.getMessage());
                    }
                }
                counterRequestForDeathMigu++;
                timeRequest -= System.currentTimeMillis();
//                serialTerminalScreen.appendText(String.valueOf(timeRequest) + "\n");

                //опрос не более 1 раза в секунду -  рассчитываеться время потраченное на запрос/ответ от МИЖУ и
                // отнимаеться от секунды, остаток от секунды ждем.
                if (timeRequest < 1000)
                    try {
                        Thread.sleep(1000 - timeRequest);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                callBack.callIsFreshMigu();
            }
//            serialTerminalScreen.appendText("Поток: " + Thread.currentThread().getName() + " остановлен\n");

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
