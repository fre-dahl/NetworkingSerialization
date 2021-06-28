package io.serialization.old;

import io.serialization.exceptions.DataRecreationException;

import static io.serialization.Serializer.*;

public class DB_Field extends DB_Entry<DB_Field> {

    private static final byte CONTAINER_TYPE = DB_Entry.FIELD;
    private byte primitiveType;
    private byte[] data;

    public DB_Field() {}

    public static DB_Field booleanField(String name, boolean value) {
        DB_Field field = new DB_Field();
        field.incHeaderSize(2);
        field.primitiveType = JavaPrimitive.BOOLEAN;
        field.setName(name);
        field.incDataSize(JavaPrimitive.size(field.primitiveType));
        field.data = new byte[field.dataSize()];
        writeBytes(field.data, value);
        return field;
    }

    public static DB_Field byteField(String name, byte value) {
        DB_Field field = new DB_Field();
        field.incHeaderSize(2);
        field.primitiveType = JavaPrimitive.BYTE;
        field.setName(name);
        field.incDataSize(JavaPrimitive.size(field.primitiveType));
        field.data = new byte[field.dataSize()];
        writeBytes(field.data, value);
        return field;
    }

    public static DB_Field shortField(String name, short value) {
        DB_Field field = new DB_Field();
        field.incHeaderSize(2);
        field.primitiveType = JavaPrimitive.SHORT;
        field.setName(name);
        field.incDataSize(JavaPrimitive.size(field.primitiveType));
        field.data = new byte[field.dataSize()];
        writeBytes(field.data, value);
        return field;
    }

    public static DB_Field charField(String name, char value) {
        DB_Field field = new DB_Field();
        field.incHeaderSize(2);
        field.primitiveType = JavaPrimitive.CHAR;
        field.setName(name);
        field.incDataSize(JavaPrimitive.size(field.primitiveType));
        field.data = new byte[field.dataSize()];
        writeBytes(field.data, value);
        return field;
    }

    public static DB_Field intField(String name, int value) {
        DB_Field field = new DB_Field();
        field.incHeaderSize(2);
        field.primitiveType = JavaPrimitive.INTEGER;
        field.setName(name);
        field.incDataSize(JavaPrimitive.size(field.primitiveType));
        field.data = new byte[field.dataSize()];
        writeBytes(field.data, value);
        return field;
    }

    public static DB_Field longField(String name, long value) {
        DB_Field field = new DB_Field();
        field.incHeaderSize(2);
        field.primitiveType = JavaPrimitive.LONG;
        field.setName(name);
        field.incDataSize(JavaPrimitive.size(field.primitiveType));
        field.data = new byte[field.dataSize()];
        writeBytes(field.data, value);
        return field;
    }

    public static DB_Field floatField(String name, float value) {
        DB_Field field = new DB_Field();
        field.incHeaderSize(2);
        field.primitiveType = JavaPrimitive.FLOAT;
        field.setName(name);
        field.incDataSize(JavaPrimitive.size(field.primitiveType));
        field.data = new byte[field.dataSize()];
        writeBytes(field.data, value);
        return field;
    }

    public static DB_Field doubleField(String name, double value) {
        DB_Field field = new DB_Field();
        field.incHeaderSize(2);
        field.primitiveType = JavaPrimitive.DOUBLE;
        field.setName(name);
        field.incDataSize(JavaPrimitive.size(field.primitiveType));
        field.data = new byte[field.dataSize()];
        writeBytes(field.data, value);
        return field;
    }

    public boolean getBool() { return readBoolean(data); }

    public byte getByte() { return data[0]; }

    public short getShort() { return readShort(data); }

    public char getChar() { return readChar(data); }

    public int getInt() { return readInt(data); }

    public long getLong() { return readLong(data); }

    public double getDouble() { return readDouble(data); }

    public float getFloat() { return readFloat(data); }

    @Override
    public void byteify(byte[] dest, int[] pointer) {
        writeBytes(dest,pointer,CONTAINER_TYPE);
        writeBytes(dest,pointer,nameSize);
        writeBytes(dest,pointer,nameData);
        writeBytes(dest,pointer,primitiveType);
        writeBytes(dest,pointer,data);
    }

    @Override
    public DB_Field recreate(byte[] data, int[] pointer) throws DataRecreationException {

        if (readByte(data,pointer) != CONTAINER_TYPE)
            throw new DataRecreationException("Unmatching Serializable Type");

        nameSize = readByte(data,pointer);
        incHeaderSize(nameSize + 3);
        nameData = new byte[nameSize];
        readByteArray(data,pointer, nameData);
        primitiveType = readByte(data,pointer);
        incDataSize(JavaPrimitive.size(primitiveType));
        this.data = new byte[dataSize()];
        readByteArray(data,pointer, this.data);

        return this;
    }
}
