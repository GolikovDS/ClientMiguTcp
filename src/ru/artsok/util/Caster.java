package ru.artsok.util;


public class Caster {
    public long byteToLong(byte b) {
        long result = 0;
        if (b < 0) {
            result = (long) 255 + b;
        } else {
            result = b;
        }
        return result;
    }

    private long signed(byte b) {
        long result = 0;
        if (b < 0) {
            result += 128 - b;
        } else {
            result += b;
        }
        return result;
    }

    public long bytesToLong111(byte[] b) {
        long result = 0;
        for (int i = b.length; i > 1; i--) {
            result += signed(b[i - 1]);
            result <<= 8;
        }
        result += signed(b[0]);
        return result;
    }

    public long bytesToLong(byte[] b) {
        long[] bb = new long[b.length];
        for (int ii = 0; ii < b.length; ii++) {
            if (b[ii] < 0) {
                bb[ii] = 255 + b[ii];
            } else {
                bb[ii] = b[ii];
            }
        }
        return (bb[3] << 24) + (bb[2] << 16) + (bb[1] << 8) + (bb[0]);
    }

    public float bytesToFloat(byte[] b) {
        long inputInt = bytesToLong(b);
        inputInt &= 0x00000000FFFFFFFFl;
        long signet = (inputInt >>> 31) == 1 ? -1 : 1;
        long exponent = inputInt >>> 23;
        long mantissa = exponent != 0 ? (inputInt & 0x7FFFFF) | 0x800000 : (inputInt & 0x7FFFFF) << 1;
        return signet * (float)((float)mantissa / 8388608.0) * (float)(Math.pow(2, (exponent - 127)));
    }
}
