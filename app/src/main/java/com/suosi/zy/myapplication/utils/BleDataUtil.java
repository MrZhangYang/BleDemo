package com.suosi.zy.myapplication.utils;

/**
 * Created by G on 2016/11/30.
 */

public class BleDataUtil {

    public static short[] getEcgData(byte[] data) {
        short[] ecgData = null;
        if (data != null && data.length == 20) {
            int len = data.length/2 ;
            ecgData = new short[2*len];

            for (int i = 0; i < len; i++) {
                short[] shortData = bleABCbyte2ShortArray(data[i % 2], data[i % 2 + 1]);
                ecgData[i*2]=shortData[0];
                ecgData[i * 2 + 1] = shortData[1];
            }
            return new short[]{ecgData[0],ecgData[1],ecgData[2],ecgData[3],
                    ecgData[4],ecgData[5]};

//            for (int i = 0; i < 6; i++) {
//                short[] shortData = bleABCbyte2ShortArray(data[i * 3 + 2], data[i * 3 + 3], data[i * 3 + 4]);
//                ecgData[i * 2] = shortData[0];
//                ecgData[i * 2 + 1] = shortData[1];
//            }
//            //6,12非ecg点
//            return new short[]{ecgData[0], ecgData[1], ecgData[2], ecgData[3],
//                    ecgData[4], ecgData[6], ecgData[7], ecgData[8], ecgData[9],
//                    ecgData[10]};
        }

        return null;
    }

    /**
     * 其中restoreA(高位)，restoreB(低位)组成short 其中restoreC(高位)，restoreD(低位)组成short
     *
     * @return short[0]为AB组合，short[1]为CD组合
     */
    private static short[] bleABCbyte2ShortArray(byte byteA, byte byteB) {
        int bitA = byteA & 0x80;
        byte restoreA = 0;
        //
        if (bitA != 0) {
            restoreA = (byte) (0xE0 | ((byteA & 0x7C) >> 2));
        } else {
            restoreA = (byte) ((byteA & 0x7C) >> 2);
        }
// restoreA = (byte) ((byteA & 0x80) | ((byteA & 0x7C) >> 2));
        byte restoreB = (byte) (((byteA & 0x03) << 6) | ((byteB & 0xF0) >> 2));


//        int bitC = byteB & 0x08;
//        byte restoreC = 0;
//        if (bitC != 0) {
//            restoreC = (byte) ((0xE0) | ((byteB & 0x07) << 2) | ((byteC & 0xC0) >> 6));
//        } else {
//            restoreC = (byte) (((byteB & 0x07) << 2) | ((byteC & 0xC0) >> 6));
//        }
//        // restoreC = (byte) (((byteB & 0x08) << 4) | ((byteB & 0x07) << 2) |
//        // ((byteC & 0xC0) >> 6));
//
//        byte restoreD = (byte) ((byteC & 0x3F) << 2);
        try {
            return new short[]{
                    ByteUtils.bytes2Short(new byte[]{restoreA, restoreB}),
                    ByteUtils.bytes2Short(new byte[]{restoreA, restoreB})};
        } catch (Exception e) {
//            LogUtil.e(TAG, e);
        }
        return null;
    }

}
