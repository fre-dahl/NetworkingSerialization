package test;

import io.serialization.database.DBEntry;
import io.serialization.database.DBField;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RndField {

    public static Random rnd = new Random();

    public static int rndInt(int min, int max) {
        return  rnd.nextInt((max - min) + 1) + min;
    }

    public static DBField create() {

        DBField field;

        switch (primitive()) {
            case DBEntry.JavaPrimitive.BOOLEAN:
                field = new DBField(varName(),rnd.nextBoolean());
                break;
            case DBEntry.JavaPrimitive.BYTE:
                field = new DBField(varName(),(byte) rndInt(1,255));
                break;
            case DBEntry.JavaPrimitive.SHORT:
                field = new DBField(varName(),(short)rndInt(1,Short.MAX_VALUE));
                break;
            case DBEntry.JavaPrimitive.CHAR:
                field = new DBField(varName(),(char)rndInt(1,Character.MAX_VALUE));
                break;
            case DBEntry.JavaPrimitive.INTEGER:
                field = new DBField(varName(),rndInt(1,Integer.MAX_VALUE));
                break;
            case DBEntry.JavaPrimitive.LONG:
                field = new DBField(varName(),rnd.nextLong());
                break;
            case DBEntry.JavaPrimitive.FLOAT:
                field = new DBField(varName(),rnd.nextFloat());
                break;
            case DBEntry.JavaPrimitive.DOUBLE:
                field = new DBField(varName(),rnd.nextDouble());
                break;
            default: return null;
        }

        return field;
    }

    public static String varName() {

        int length = rndInt(1,10);
        byte[] chars = new byte[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (byte)(rndInt(65,122));
        }

        return new String(chars, StandardCharsets.UTF_8);
    }

    public static byte primitive() {
        return (byte) rndInt(1,8);
    }
}
