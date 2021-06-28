package io.serialization.transmission.old;

public class DatabasePacket extends PacketData<DatabasePacket>{


    public static final byte PACKET_TYPE = TYPE_DATABASE_PACKET;
    public static final byte HEADER_SIZE = 2;


    private byte[] data;



    @Override
    public void byteify(byte[] dest, int[] pointer) {

    }

    @Override
    public DatabasePacket recreate(byte[] data, int[] pointer) {
        return null;
    }

    @Override
    public int headerSize() {
        return HEADER_SIZE;
    }

    @Override
    public int dataSize() {
        return data.length;
    }

}
