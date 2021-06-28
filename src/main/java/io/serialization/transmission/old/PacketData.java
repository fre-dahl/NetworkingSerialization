package io.serialization.transmission.old;

import io.serialization.Serializer;
import io.serialization.old.adt.SerializableOld;

public abstract class PacketData<T extends SerializableOld<T>> extends SerializableOld<T> {

    public static final byte TYPE_INVALID               = 0x00;
    public static final byte TYPE_TEXT_MESSAGE          = 0x01;
    public static final byte TYPE_DATABASE_TRANSMISSION = 0x02;
    public static final byte TYPE_DATABASE_PACKET       = 0x03;

    public static final byte TYPE_VALID_MIN             = 0x01;
    public static final byte TYPE_VALID_MAX             = 0x02;

    public static final byte PACKET_HEADER              = (byte) 0xAA;

    private static final int HEADER_SLOT                = 0x00;
    private static final int DATA_TYPE_SLOT             = 0x01;


    public static boolean validateHeader(byte[] data) {
        return  data[HEADER_SLOT] == PACKET_HEADER;
    }

    public static byte dataType(byte[] data) {
        byte dataType = data[DATA_TYPE_SLOT];
        if (dataType >= TYPE_VALID_MIN && dataType <= TYPE_VALID_MAX)
            return dataType;
        return TYPE_INVALID;
    }

    public static byte partitionData(byte part, byte sum) {
        part--; sum--;
        return Serializer.merge(sum,part);
    }

    public static byte partitionID(byte partitionData) {
        byte result = Serializer.lsb4(partitionData);
        result++;
        return result;
    }

    public static byte partitionSum(byte partitionData) {
        byte result = Serializer.msb4(partitionData);
        result++;
        return result;
    }

}
