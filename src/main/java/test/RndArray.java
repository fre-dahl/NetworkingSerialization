package test;

import io.serialization.database.DBArray;
import io.serialization.database.DBEntry;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RndArray {

    public static Random rnd = new Random();

    public static int rndInt(int min, int max) {
        return  rnd.nextInt((max - min) + 1) + min;
    }

    public static DBArray create() {

        DBArray array;

        switch (primitive()) {
            case DBEntry.JavaPrimitive.BOOLEAN:
                array = new DBArray(varName(),boolArr());
                break;
            case DBEntry.JavaPrimitive.BYTE:
                array = new DBArray(varName(),byteArr());
                break;
            case DBEntry.JavaPrimitive.SHORT:
                array = new DBArray(varName(),shortArr());
                break;
            case DBEntry.JavaPrimitive.CHAR:
                array = new DBArray(varName(),charArr());
                break;
            case DBEntry.JavaPrimitive.INTEGER:
                array = new DBArray(varName(),intArr());
                break;
            case DBEntry.JavaPrimitive.LONG:
                array = new DBArray(varName(),longArr());
                break;
            case DBEntry.JavaPrimitive.FLOAT:
                array = new DBArray(varName(),floatArr());
                break;
            case DBEntry.JavaPrimitive.DOUBLE:
                array = new DBArray(varName(),doubleArr());
                break;
            default: return null;
        }

        return array;
    }

    public static String varName() {

        int length = rndInt(1,10);
        byte[] chars = new byte[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (byte)(rndInt(65,122));
        }

        return new String(chars, StandardCharsets.UTF_8);
    }

    public static boolean[] boolArr() {
        int length = rndInt(0,100);
        boolean[] result = new boolean[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = rnd.nextBoolean();
        }
        return result;
    }

    public static byte[] byteArr() {
        int length = rndInt(0,100);
        byte[] result = new byte[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte)rndInt(1,255);
        }
        return result;
    }

    public static short[] shortArr() {
        int length = rndInt(0,50);
        short[] result = new short[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (short)rndInt(1,Short.MAX_VALUE);
        }
        return result;
    }

    public static char[] charArr() {
        int length = rndInt(0,50);
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (char)rndInt(1,Character.MAX_VALUE);
        }
        return result;
    }

    public static int[] intArr() {
        int length = rndInt(0,25);
        int[] result = new int[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = rndInt(1,Integer.MAX_VALUE);
        }
        return result;
    }

    public static long[] longArr() {
        int length = rndInt(0,15);
        long[] result = new long[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = rnd.nextLong();
        }
        return result;
    }

    public static float[] floatArr() {
        int length = rndInt(0,25);
        float[] result = new float[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = rnd.nextFloat();
        }
        return result;
    }

    public static double[] doubleArr() {
        int length = rndInt(0,15);
        double[] result = new double[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = rnd.nextDouble();
        }
        return result;
    }

    public static byte primitive() {
        return (byte) rndInt(1,8);
    }
}
