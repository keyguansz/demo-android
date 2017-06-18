/**
 * @file_name    : DJITextUtil.java
 * @package_name : com.util
 * @Data         : 2013-10-25 下午4:44:16
 * @author       : zhangliutao
 * @Description  : TODO
 *
 * Copyright (c) 2013, DJI All Rights Reserved.
 */

package k.core.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DJITextUtil
{
    public static byte getUnsignedBytes(short data) {
        return (byte) (data & 0xff);
    }

    public static byte[] getBytes(short data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    public static byte[] getBytes(char data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data);
        bytes[1] = (byte) (data >> 8);
        return bytes;
    }

    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        bytes[2] = (byte) ((data & 0xff0000) >> 16);
        bytes[3] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }

    public static byte[] getUnsignedBytes(int data) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    public static byte getByte(int data) {
        return (byte) (data & 0xff);
    }

    public static byte[] getBytes(long data) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        bytes[4] = (byte) ((data >> 32) & 0xff);
        bytes[5] = (byte) ((data >> 40) & 0xff);
        bytes[6] = (byte) ((data >> 48) & 0xff);
        bytes[7] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }

    public static byte[] getUnsignedBytes(long data) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (data & 0xff);
        bytes[1] = (byte) ((data >> 8) & 0xff);
        bytes[2] = (byte) ((data >> 16) & 0xff);
        bytes[3] = (byte) ((data >> 24) & 0xff);
        return bytes;
    }

    public static byte[] getBytes(float data) {
        int intBits = Float.floatToIntBits(data);
        return getBytes(intBits);
    }

    public static byte[] getUnsignedBytes(float data) {
        int intBits = Float.floatToIntBits(data);
        return getUnsignedBytes(intBits);
    }

    public static byte[] getBytes(double data) {
        long intBits = Double.doubleToLongBits(data);
        return getBytes(intBits);
    }

    public static byte[] getUnsignedBytes(double data) {
        long intBits = Double.doubleToLongBits(data);
        return getUnsignedBytes(intBits);
    }

    private static byte[] getBytes(String data, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    public static byte[] getBytes(String data) {
        return getBytes(data, "GBK");
    }

    public static byte[] getBytesUTF8(String data) {
        return getBytes(data, "UTF-8");
    }

    public static short getInt(byte b) {
        return (short) (0xff & b);
    }

    public static short getSignedInt(byte b) {
        return b;
    }

    public static int getInt(short b) {
        return (0xffff & b);
    }

    public static int getSignedInt(short b) {
        return b;
    }

    public static short getShort(byte[] bytes) {
        return ByteBuffer.wrap(fillBytes(bytes, 2)).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public static int getInt(byte[] bytes) {
        return ByteBuffer.wrap(fillBytes(bytes, 4)).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static long getLong(byte[] bytes) {
        return ByteBuffer.wrap(fillBytes(bytes, 8)).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }

    public static float getFloat(byte[] bytes) {
        return ByteBuffer.wrap(fillBytes(bytes, 4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static double getDouble(byte[] bytes) {
        return ByteBuffer.wrap(fillBytes(bytes, 8)).order(ByteOrder.LITTLE_ENDIAN).getDouble();
    }

    public static short getShort(byte[] bytes, int start) {
        return ByteBuffer.wrap(fillBytes(bytes, 2), start, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public static int getInt(byte[] bytes, int start) {
        return ByteBuffer.wrap(fillBytes(bytes, 4), start, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static long getLong(byte[] bytes, int start) {
        return ByteBuffer.wrap(fillBytes(bytes, 8), start, 8).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }

    public static float getFloat(byte[] bytes, int start) {
        return ByteBuffer.wrap(fillBytes(bytes, 4), start, 4).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    public static double getDouble(byte[] bytes, int start) {
        return ByteBuffer.wrap(fillBytes(bytes, 8), start, 8).order(ByteOrder.LITTLE_ENDIAN).getDouble();
    }

    public static int getUShort(byte[] buffer, int start) {
        byte[] bf = new byte[2];
        System.arraycopy(buffer, start, bf, 0, 2);
        return ByteBuffer.wrap(fillBytes(bf, 4), 0, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public static long getUInt(byte[] buffer, int start) {
        byte[] bf = new byte[4];
        System.arraycopy(buffer, start, bf, 0, 4);
        return ByteBuffer.wrap(fillBytes(bf, 8), 0, 8).order(ByteOrder.LITTLE_ENDIAN).getLong();
    }

    private static byte[] fillBytes(byte[] bytes, int maxLen) {
        int len = maxLen-bytes.length;
        if(len>0) {
            bytes = arrayComb(bytes, new byte[len]);
        }
        return bytes;
    }

    public static String getString(byte[] bytes, String charsetName) {
        return new String(bytes, Charset.forName(charsetName));
    }

    private static String getString(byte[] bytes, int start, int length, String charsetName) {
        return new String(bytes, start, length, Charset.forName(charsetName));
    }

    public static String getString(byte[] bytes, int start, int length) {
        // 去除NULL字符
        byte zero = 0x00;
        for (int i = start; i < length; i++) {
            if (bytes[i] == zero) {
                length = i-start;
                break;
            }
        }
        return getString(bytes, start, length, "GBK");
    }

    public static String getStringUTF8(byte[] bytes, int start, int length) {
        // 去除NULL字符
        byte zero = 0x00;
        for (int i = start; i < length; i++) {
            if (bytes[i] == zero) {
                length = i-start;
                break;
            }
        }
        return getString(bytes, start, length, "UTF-8");
    }

    public static String getStringUTF8Offset(final byte[] bytes, final int offset, int length) {
        if (null == bytes || bytes.length == 0) {
            return "";
        }
        for (int i = offset; i < (offset + length) && i < bytes.length; i++) {
            if (bytes[i] == 0x00) {
                length = i - offset;
                break;
            }
        }
        return new String(bytes, offset, length, Charset.forName("UTF-8"));
    }

    public static String getString(byte[] bytes) {
        // 去除NULL字符
        byte zero = 0x00;
        byte no = (byte)0xFF;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == zero || bytes[i] == no) {
                bytes = readBytes(bytes, 0, i);
                break;
            }
        }
        return getString(bytes, "GBK");
    }

    public static String getStringUTF8(byte[] bytes) {
        // 去除NULL字符
        byte zero = 0x00;
        byte no = (byte)0xFF;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == zero || bytes[i] == no) {
                bytes = readBytes(bytes, 0, i);
                break;
            }
        }
        return getString(bytes, "UTF-8");
    }

    public static String byte2hexNoSep(byte[] buffer) {
        String h = "";

        for (int i = 0; i < buffer.length; i++) {
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            h += temp;
        }

        return h;
    }

    public static String byte2hex(byte[] buffer) {
        String h = "";

        for (int i = 0; i < buffer.length; i++) {
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            h = h + " " + temp;
        }

        return h;
    }

    public static String byte2hex(byte[] buffer, int start, int length) {
        String h = "";

        for (int i = start; i < start+length; i++) {
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            h = h + " " + temp;
        }

        return h;
    }

    public static String byte2hex(byte[] buffer, String sep) {
        String h = "";

        for (int i = 0; i < buffer.length; i++) {
            String temp = Integer.toHexString(buffer[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            if (i==0) {
                h = h + temp;
            }else {
                h = h + sep + temp;
            }
        }

        return h;
    }

    public static String byte2hex(byte buffer) {
        String h = Integer.toHexString(buffer & 0xFF);
        if (h.length() == 1) {
            h = "0" + h;
        }
        return h;
    }

    public static String byte2hex(int buffer) {
        String h = Integer.toHexString(buffer & 0xFF);
        if (h.length() == 1) {
            h = "0" + h;
        }
        return h;
    }

    /**
     * 把byte数组转化成2进制字符串
     *
     * @param bArr
     * @return
     */
    public static String getBinaryStrFromByteArr(byte[] bArr) {
        String result = "";
        for (byte b : bArr) {
            result += getBinaryStrFromByte(b);
        }
        return result;
    }

    /**
     * 把byte转化成2进制字符串
     *
     * @param b
     * @return
     */
    public static String getBinaryStrFromByte(byte b) {
        String result = "";
        byte a = b;
        for (int i = 0; i < 8; i++) {
            result = (a % 2) + result;
            a = (byte) (a >> 1);
        }
        return result;
    }

    public static byte[] hex2byte(String str) {
        if (str == null){
            return null;
        }

        str = str.trim();
        int len = str.length();

        if (len == 0 || len % 2 == 1){
            return null;
        }

        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] readBytes(byte[] source, int from, int length) {
        byte[] result = new byte[length];
        System.arraycopy(source, from, result, 0, length);
        /**
         for (int i = 0; i < length; i++) {
         result[i] = source[from + i];
         }
         */
        return result;
    }

    public static byte[] arrayComb(byte[] prep, byte[] after) {
        byte[] result = new byte[prep.length + after.length];
        System.arraycopy(prep, 0, result, 0, prep.length);
        System.arraycopy(after, 0, result, prep.length, after.length);
        return result;
    }

    public static byte[] arrayApend(byte[] prep, byte after) {
        byte[] result = new byte[prep.length + 1];
        System.arraycopy(prep, 0, result, 0, prep.length);
        result[prep.length] = after;
        return result;
    }

    public static byte[] arrayRemove(byte[] source, int len) {
        int lenNow = source.length - len;
        byte[] result = new byte[lenNow];
        System.arraycopy(source, len, result, 0, lenNow);
        return result;
    }

    public static byte[] arrayPop(byte[] source, int len) {
        int lenNow = source.length - len;
        byte[] result = new byte[lenNow];
        System.arraycopy(source, 0, result, 0, lenNow);
        return result;
    }

    public static byte[] arraycopy(byte[] from, byte[] to) {
        System.arraycopy(from, 0, to, 0, from.length);
        return to;
    }

    public static byte[] arraycopy(byte[] from, byte[] to, int toPos) {
        System.arraycopy(from, 0, to, toPos, from.length);
        return to;
    }

    public static byte parseBcd(int t) {
        return (byte) ((t/10)*0x10 + t%10);
    }

    private static final int[] INTERVAL = new int[] {'0', '9', 'A', 'Z', 'a', 'z'};

    public static boolean isNumberOrLetter(final byte value) {
        return ((INTERVAL[0] <= value && value <= INTERVAL[1])
                || (INTERVAL[2] <= value && value <= INTERVAL[3]) || (INTERVAL[4] <= value && value <= INTERVAL[5]));
    }
    /**
     *@verId 1
     *@desc
     *@author : key.guan
     *@date   : 2016/9/8 20:23
     */
    public static byte getH(byte b) {
        return (byte) (b >>> 5);
    }

    public static byte getL(byte b) {
        return (byte) (b & 0x0F);
    }
    /**
     * @verId 1
     * @desc 格式化成为月日的形式,注意y一定要*1000，MM大写。
     * @author : key.guan
     * @date : 2016/9/1 19:51
     */
    private static final DateFormat mDateFormat = new SimpleDateFormat("MM.dd");
    public static String formatMd(String longTime) {
        Date date = new Date(Long.parseLong(longTime)*1000);
        return mDateFormat.format(date);
    }
    public static boolean isWifiViaMobile(int ip){
        String ipStr = formatIpAddress(ip);
        String ipMin = "192.168.42.2";
        String ipMax = "192.168.48.254";
        boolean isFlg = ipMin.compareTo(ipStr) <= 0 && ipMax.compareTo(ipStr) >= 0;

        return isFlg;
    }
    private static String formatIpAddress(int ipAdress) {
        return (ipAdress & 0xFF ) + "." +
                ((ipAdress >> 8 ) & 0xFF) + "." +
                ((ipAdress >> 16 ) & 0xFF) + "." +
                ( ipAdress >> 24 & 0xFF) ;
    }


    //region:just bak,you can delete
    public static boolean checkIp( String ipStr ){
        String ipMin = "192.168.42.2";
        String ipMax = "192.168.48.254";
        return ipMin.compareTo(ipStr) <= 0 && ipMax.compareTo(ipStr) >= 0;
    }
    /**
     *@verId 1
     *@desc   nVersion的32位，0-3.4-7.8-11.12-15
     *@author : key.guan
     *@date   : 2016/9/10 13:00
     */
    public static String parseVersion(int nVersion) {
        final int SUBVERSION_BITS = 4;//每个子版本号所占字位数
        final int SUBVersion_StrLen = 2;//每个子版本号转化成为字符串形式位数,可以通过mask计算，但是挺耗性能的，因此这里设死，依赖SUBVersion_BITs
        final int SUBVersion_NUM = 4;//子版本号数目
        int mask = (1 << SUBVERSION_BITS) - 1;
        StringBuilder sb = new StringBuilder((SUBVersion_StrLen+1)*SUBVersion_NUM-1);//4*2+3=11
        for(int i = 0; i < SUBVersion_NUM; i++){
            sb.append(".").append( nVersion & mask);
            nVersion = nVersion >>> SUBVERSION_BITS;
        }
        return sb.reverse().substring(0, sb.length()-1);
    }
    //endregion
}
