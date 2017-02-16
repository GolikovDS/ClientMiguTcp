package ru.artsok.util;


public class Single {

    public static float get(long inputInt) {
        float f = inputInt;
        inputInt &= 0x00000000FFFFFFFFl;
        long signet = (inputInt >>> 31) == 1 ? -1 : 1;
        long exponent = inputInt >>> 23;
        long mantissa = exponent != 0 ? (inputInt & 0x7FFFFF) | 0x800000 : (inputInt & 0x7FFFFF) << 1;
        System.out.println(((float)mantissa / 8388608.0));
        return signet * (float)((float)mantissa / 8388608.0) * (float)(Math.pow(2, (exponent - 127)));
    }

}
