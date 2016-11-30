package com.suosi.zy.myapplication.utils;

/**
 * Created by G on 2016/11/30.
 */

public class ByteUtils {

    private static String hexStr =  "0123456789ABCDEF";  //全局



//    1、二进制字节转十六进制时，将字节高位与0xF0做"&"操作,然后再左移4位，得到字节高位的十六进制A;
//       将字节低位与0x0F做"&"操作，得到低位的十六进制B，将两个十六进制数拼装到一块AB就是该字节的十六进制表示。
//    2、十六进制转二进制字节时，将十六进制字符对应的十进制数字右移动4为，得到字节高位A;
//       将字节低位的十六进制字符对应的十进制数字B与A做"|"运算，即可得到十六进制的二进制字节表示

    /**
     * 二进制转换成十六进制string
     * @param bytes
     * @return
     */
    public static String BinaryToHexString(byte[] bytes) {
        String result="";
        String hex="";
        for (int i = 0; i < bytes.length; i++) {
            //字节高四位
            hex=String.valueOf(hexStr.charAt(bytes[i]&0xF0)>>4);
            hex+=String.valueOf(hexStr.charAt(bytes[i]&0x0F));
            result+=hex;
        }
        return result;
    }

    /**
     * 十六进制转换成byte
     * @param hexString
     * @return
     */
    public static byte[] HexStringToBinary(String hexString){
        int len=hexString.length()/2;
        byte[] bytes=new byte[len];

        byte high=0;//字节高四位
        byte low=0;//字节低四位
        for (int i = 0; i < len; i++) {
            //右移得到高四位
            high= (byte) (hexStr.indexOf(hexString.charAt(2*i))<<4);
            low= (byte) hexStr.indexOf(hexString.charAt(2*i+1));
            bytes[i]=(byte)(high|low);////高地位做或运算
        }
        return bytes;
    }

    /**Convert byte[] to hex string.
     * 这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
    * @param src byte[] data
    * @return hex string
    */
    //上面是将byte[]转化十六进制的字符串,注意这里b[ i ] & 0xFF将一个byte和 0xFF进行了与运算,然后使用Integer.toHexString取得了十六进制字符串,可以看出
//    b[ i ] & 0xFF运算后得出的仍然是个int,那么为何要和 0xFF进行与运算呢?直接 Integer.toHexString(b[ i ]);,将byte强转为int不行吗?答案是不行的.
//    其原因在于:
//    1.byte的大小为8bits而int的大小为32bits
//    2.java的二进制采用的是补码形式
//    所以与负数&的时候负数会自动给补码补位1，这样就会有误差
//    而0xff默认是整形，所以，一个byte跟0xff相与会先将那个byte转化成整形运算，这样，结果中的高的24个比特就总会被清0
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


//    字符串转换成十六进制字符串方法1：

    /**
     * 字符串转换成十六进制字符串
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString();
    }



//    十六进制字符串转换成为数组方法1：

    /**
     * 把16进制字符串转换成字节数组
     * @param hexString
     * @return byte[]
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

//    数组转换成十六进制字符串方法1：

    /**
     * 数组转换成十六进制字符串
     * @param byte[]
     * @return HexString
     */
    public static final String bytes2HexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

//    byte[]数组转换成十六进制字符串方法2：

    /**
     * 数组转成十六进制字符串
     * @param byte[]
     * @return HexString
     */
    public static String toHexString1(byte[] b){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i){
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }
    public static String toHexString1(byte b){
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        }else{
            return s;
        }
    }

//    十六进制字符串转换字符串方法1：

    /**
     * 十六进制字符串转换成字符串
     * @param hexString
     * @return String
     */
    public static String hexStr2Str(String hexStr) {

        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }



//    十六进制字符串转换字符串方法2：

    /**
     * 十六进制字符串转换字符串
     * @param HexString
     * @return String
     */
    public static String toStringHex(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                        i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


    public static short bytes2Short(byte[] byteArray) throws Exception {
        if (byteArray==null)
            throw new Exception("参数不能为空");
        if (byteArray.length!=2)
            throw new Exception("参数长度必须为2字节");
        //默认填充0
        byte[] b=new byte[]{0,0};
        //按照参数byteArray从高位到低位填充到b[]中
        int len=2;
        for (int i = 0; i < len; i++) {
            b[i]=byteArray[i];
        }
        return (short) ((b[1] & 0xFF) | (b[0] & 0xFF) << 8);
    }



}
