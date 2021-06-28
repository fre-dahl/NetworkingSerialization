package io.serialization.old;

import io.serialization.exceptions.DataRecreationException;

import static io.serialization.old.DB_Entry.JavaPrimitive.*;
import static io.serialization.Serializer.writeBytes;
import static io.serialization.Serializer.*;

public class DB_Array extends DB_Entry<DB_Array> {

    private static final byte CONTAINER_TYPE = DB_Entry.ARRAY;
    private byte primitiveType;
    private int count;

    private boolean[] boolData;
    private byte[] byteData;
    private short[] shortData;
    private char[] charData;
    private int[] intData;
    private long[] longData;
    private float[] floatData;
    private double[] doubleData;

    public DB_Array() {}

    public static DB_Array boolArray(String name, boolean[] data) {
        DB_Array array = new DB_Array();
        array.incHeaderSize(6);
        array.setName(name);
        array.primitiveType = JavaPrimitive.BOOLEAN;
        array.count = data.length;
        array.boolData = data;
        array.incDataSize((int)(Math.ceil(array.count/8f)* JavaPrimitive.size(array.primitiveType)));
        return array;
    }

    public static DB_Array byteArray(String name, byte[] data) {
        DB_Array array = new DB_Array();
        array.incHeaderSize(6);
        array.setName(name);
        array.primitiveType = JavaPrimitive.BYTE;
        array.count = data.length;
        array.byteData = data;
        array.incDataSize(array.count * JavaPrimitive.size(array.primitiveType));
        return array;
    }

    public static DB_Array shortArray(String name, short[] data) {
        DB_Array array = new DB_Array();
        array.incHeaderSize(6);
        array.setName(name);
        array.primitiveType = JavaPrimitive.SHORT;
        array.count = data.length;
        array.shortData = data;
        array.incDataSize(array.count * JavaPrimitive.size(array.primitiveType));
        return array;
    }

    public static DB_Array charArray(String name, char[] data) {
        DB_Array array = new DB_Array();
        array.incHeaderSize(6);
        array.setName(name);
        array.primitiveType = JavaPrimitive.CHAR;
        array.count = data.length;
        array.charData = data;
        array.incDataSize(array.count * JavaPrimitive.size(array.primitiveType));
        return array;
    }

    public static DB_Array intArray(String name, int[] data) {
        DB_Array array = new DB_Array();
        array.incHeaderSize(6);
        array.setName(name);
        array.primitiveType = JavaPrimitive.INTEGER;
        array.count = data.length;
        array.intData = data;
        array.incDataSize(array.count * JavaPrimitive.size(array.primitiveType));
        return array;
    }

    public static DB_Array longArray(String name, long[] data) {
        DB_Array array = new DB_Array();
        array.incHeaderSize(6);
        array.setName(name);
        array.primitiveType = JavaPrimitive.LONG;
        array.count = data.length;
        array.longData = data;
        array.incDataSize(array.count * JavaPrimitive.size(array.primitiveType));
        return array;
    }

    public static DB_Array floatArray(String name, float[] data) {
        DB_Array array = new DB_Array();
        array.incHeaderSize(6);
        array.setName(name);
        array.primitiveType = JavaPrimitive.FLOAT;
        array.count = data.length;
        array.floatData = data;
        array.incDataSize(array.count * JavaPrimitive.size(array.primitiveType));
        return array;
    }

    public static DB_Array doubleArray(String name, double[] data) {
        DB_Array array = new DB_Array();
        array.incHeaderSize(6);
        array.setName(name);
        array.primitiveType = JavaPrimitive.DOUBLE;
        array.count = data.length;
        array.doubleData = data;
        array.incDataSize(array.count * JavaPrimitive.size(array.primitiveType));
        return array;
    }

    public boolean[] boolData() {
        return boolData;
    }

    public byte[] byteData() {
        return byteData;
    }

    public short[] shortData() {
        return shortData;
    }

    public char[] charData() {
        return charData;
    }

    public int[] intData() {
        return intData;
    }

    public long[] longData() {
        return longData;
    }

    public float[] floatData() {
        return floatData;
    }

    public double[] doubleData() {
        return doubleData;
    }

    @Override
    public void byteify(byte[] dest, int[] pointer) {

        writeBytes(dest,pointer,CONTAINER_TYPE);
        writeBytes(dest,pointer,nameSize);
        writeBytes(dest,pointer, nameData);
        writeBytes(dest,pointer,count);
        writeBytes(dest,pointer, primitiveType);

        switch (primitiveType) {
            case BOOLEAN:   writeBytes(dest,pointer,boolData);   break;
            case BYTE:      writeBytes(dest,pointer,byteData);   break;
            case SHORT:     writeBytes(dest,pointer,shortData);  break;
            case CHAR:      writeBytes(dest,pointer,charData);   break;
            case INTEGER:   writeBytes(dest,pointer,intData);    break;
            case LONG:      writeBytes(dest,pointer,longData);   break;
            case FLOAT:     writeBytes(dest,pointer,floatData);  break;
            case DOUBLE:    writeBytes(dest,pointer,doubleData); break;
        }
    }

    @Override
    public DB_Array recreate(byte[] data, int[] pointer) throws DataRecreationException {

        if (readByte(data,pointer) != CONTAINER_TYPE)
            throw new DataRecreationException("Unmatching Serializable Type");

        nameSize = readByte(data,pointer);
        nameData = new byte[nameSize];
        incHeaderSize(nameSize + 7);
        readByteArray(data,pointer, nameData);
        count = readInt(data,pointer);
        primitiveType = readByte(data,pointer);

        switch (primitiveType) {
            case BOOLEAN:
                boolData = new boolean[count];
                readBoolArray(data,pointer,boolData);
                incDataSize((int)(Math.ceil(count/8f)* JavaPrimitive.size(primitiveType)));
                break;
            case BYTE:
                byteData = new byte[count];
                readByteArray(data,pointer,byteData);
                incDataSize(count * JavaPrimitive.size(primitiveType));
                break;
            case SHORT:
                shortData = new short[count];
                readShortArray(data,pointer,shortData);
                incDataSize(count * JavaPrimitive.size(primitiveType));
                break;
            case CHAR:
                charData = new char[count];
                readCharArray(data,pointer,charData);
                incDataSize(count * JavaPrimitive.size(primitiveType));
                break;
            case INTEGER:
                intData = new int[count];
                readIntArray(data,pointer,intData);
                incDataSize(count * JavaPrimitive.size(primitiveType));
                break;
            case LONG:
                longData = new long[count];
                readLongArray(data,pointer,longData);
                incDataSize(count * JavaPrimitive.size(primitiveType));
                break;
            case FLOAT:
                floatData = new float[count];
                readFloatArray(data,pointer,floatData);
                incDataSize(count * JavaPrimitive.size(primitiveType));
                break;
            case DOUBLE:
                doubleData = new double[count];
                readDoubleArray(data,pointer,doubleData);
                incDataSize(count * JavaPrimitive.size(primitiveType));
                break;
            default: throw new DataRecreationException("Unknown datatype");
        }

        return this;
    }
}
