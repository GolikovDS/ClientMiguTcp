package ru.artsok.SerialPort;


import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
import ru.artsok.Main;
import ru.artsok.entity.Migu;
import ru.artsok.entity.interfaces.MiguHandle;

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
        void calling(byte[] bytes, Integer numberMigu);

        void callIsFreshMigu();
    }

    UpdateDataCallBack callBack;

    public void registerCallBack(UpdateDataCallBack callBack) {
        this.callBack = callBack;
    }

    public synchronized byte[] getData(List<Byte> request, int sizeResponse) throws SerialPortException, SerialPortTimeoutException, ExceptionCRC {
        CRC16 crc16 = new CRC16(request);
        serialPort.writeBytes(crc16.getDataAndCrc());
        byte[] result = serialPort.readBytes(sizeResponse, 700);
        byte[] check = new CRC16(Arrays.copyOfRange(result, 0, result.length - 2)).getDataAndCrc();
        if (check[check.length - 1] == result[result.length - 1] && check[check.length - 2] == result[result.length - 2]) {
            return Arrays.copyOfRange(result, 3, result.length - 2);
        } else {
            throw new ExceptionCRC();
        }
    }

    public void readAllRegMigu(UpdateDataCallBack callBack) {
        Thread thread = new Thread(() ->
        {
            registerCallBack(callBack);
            long timeRequest = 0;
            int counterRequestForDeathMigu = 0;
            byte length = 0x4A;

            for (Migu migu : MiguHandle.miguMap.getMap().values())
                migu.getStates().setMiguIsRespond(true);

            while (serialPort.isOpened() && Main.appIsLive) {

                timeRequest = System.currentTimeMillis();
                for (Migu migu : MiguHandle.miguMap.getMap().values()) {
                    if (migu.getStates().isSelect() || counterRequestForDeathMigu == 0) {
                        try {

                            callBack.calling(portMigu.getData(Arrays.asList(new Byte[]{(byte) migu.getAddress(), 0x44, 0x00, 0x00, 0x00, length}), length * 2 + 5), migu.getNumber());
                            migu.getStates().setMiguIsRespond(true);
                        } catch (SerialPortTimeoutException | SerialPortException e) {
                            System.err.println("Exception !!!" + e.getMessage());
                            migu.getStates().setMiguIsRespond(false);
                        } catch (ExceptionCRC exceptionCRC) {
                            exceptionCRC.printStackTrace();
                        }
                    }
                }
                //проверка раз в 5 секунд что МИЖУ не отвечает
                if (counterRequestForDeathMigu == 5) {
                    counterRequestForDeathMigu = 0;
                    for (Migu deathMiguAddres : MiguHandle.miguMap.getMap().values()) {
                        try {
                            portMigu.getData(Arrays.asList(new Byte[]{(byte) deathMiguAddres.getAddress(), 0x44, 0x00, 0x00, 0x00, length}), length * 2 + 5);
                            deathMiguAddres.getStates().setMiguIsRespond(true);
                        } catch (SerialPortException | SerialPortTimeoutException e) {
                            deathMiguAddres.getStates().setMiguIsRespond(false);
                            System.err.println("Нет ответа от МИЖУ №" + deathMiguAddres + " \nОшибка: " + e.getMessage());
                        } catch (ExceptionCRC exceptionCRC) {
                            exceptionCRC.printStackTrace();
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
