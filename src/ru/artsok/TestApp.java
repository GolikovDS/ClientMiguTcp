package ru.artsok;


import java.util.ArrayList;
import java.util.List;

public class TestApp {

    public static void main(String[] args) {
        byte[] b = new byte[]{0x00, 0x00, 0x00, 0x00, 0x01};
        List<Integer> integers = new TestApp().getErrOrEvent(b);
        integers.forEach(System.out::println);
    }

    public List<Integer> getErrOrEvent(byte[] bytes) {
        List<Integer> result = new ArrayList<>();
        boolean[] flag;
        for (int i = 0; i < bytes.length; i++) {
            flag = getFlags(bytes[i]);
            for (int j = 0; j < 8; j++) {
                if (flag[j]) {
                    result.add(i * 8 + j);
                }
            }
        }
        return result;
    }

    public boolean[] getFlags(byte b) {
        boolean[] result = new boolean[8];

        for (int i = 0; i < 8; i++) {
            result[i] = ((b >>> i) & 0x01) == 1;
        }
        return result;
    }
}
