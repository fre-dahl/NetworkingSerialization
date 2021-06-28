package io.serialization.transmission.old;

import io.serialization.old.Database;
import io.serialization.exceptions.DataRecreationException;

import java.util.List;

import static io.serialization.Serializer.readByte;
import static io.serialization.Serializer.writeBytes;

public class DatabaseTransmission extends PacketData<DatabaseTransmission>{

    public static final byte PACKET_TYPE = TYPE_DATABASE_TRANSMISSION;
    public static final byte HEADER_SIZE = 7;
    private byte packetCount;
    private int transmissionHash;

    private List<DatabasePacket> packets;
    private Database database;


    public static DatabaseTransmission create(Database database) {
        DatabaseTransmission databaseTransmission = new DatabaseTransmission();
        databaseTransmission.database = database;
        return databaseTransmission;
    }

    @Override
    public void byteify(byte[] dest, int[] pointer) {
        writeBytes(dest,pointer, PACKET_HEADER);
        writeBytes(dest,pointer,PACKET_TYPE);
        database.byteify(dest,pointer);
    }

    public DatabaseTransmission recreate(byte[] data) {
        return recreate(data,new int[1]);
    }

    @Override
    public DatabaseTransmission recreate(byte[] data, int[] pointer) {

        if (readByte(data,pointer) != PACKET_HEADER)
            throw new DataRecreationException("Invalid Common Header");

        if (readByte(data,pointer) != PACKET_TYPE)
            throw new DataRecreationException("Invalid PacketType");

        database = new Database().recreate(data,pointer);

        return this;
    }

    public byte packetCount() {
        return packetCount;
    }

    public int transmissionHash() {
        return transmissionHash;
    }

    public Database getDatabase() {
        return database;
    }

    @Override
    public int headerSize() {
        return HEADER_SIZE;
    }

    @Override
    public int dataSize() {
        return database.size();
    }
}
