package io.serialization.old;


import io.serialization.old.adt.SerializableOld;

import java.nio.charset.StandardCharsets;

public abstract class DB_Entry<T extends SerializableOld<T>> extends SerializableOld<T> {

    public static class JavaPrimitive {

        public static final byte UNKNOWN = 0;
        public static final byte BOOLEAN = 1;
        public static final byte BYTE    = 2;
        public static final byte SHORT   = 3;
        public static final byte CHAR    = 4;
        public static final byte INTEGER = 5;
        public static final byte LONG    = 6;
        public static final byte FLOAT   = 7;
        public static final byte DOUBLE  = 8;

        public static int size(byte type) {
            switch (type) {
                case BOOLEAN:
                case BYTE:      return 1;
                case SHORT:
                case CHAR:      return 2;
                case INTEGER:
                case FLOAT:     return 4;
                case LONG:
                case DOUBLE:    return 8;
                default:        return 0;
            }
        }
    }

    public static final byte FIELD      = 1;
    public static final byte STRING     = 2;
    public static final byte ARRAY      = 3;
    public static final byte OBJECT     = 4;
    public static final byte DATABASE   = 5;

    private int headerSize = 0;
    private int payloadSize = 0;

    private String name;
    protected byte[] nameData;
    protected byte nameSize;


    public void setName(String name) {
        this.name = name;
        this.nameData = name.getBytes(StandardCharsets.UTF_8);
        nameSize = (byte) nameData.length;
        incHeaderSize(nameSize + 1);
    }

    public String name() {
        if (name == null) {
            name = new String(
                    nameData,
                    0,
                    nameData.length,
                    StandardCharsets.UTF_8);
        }
        return name;
    }

    public void incHeaderSize(int amount) {
        headerSize += amount;
    }

    public void incDataSize(int amount) {
        payloadSize += amount;
    }

    @Override
    public int size() {
        return headerSize() + dataSize();
    }

    @Override
    public int headerSize() {
        return headerSize;
    }

    @Override
    public int dataSize() {
        return payloadSize;
    }

}
