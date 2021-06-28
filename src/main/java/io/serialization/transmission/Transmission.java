package io.serialization.transmission;

import io.serialization.old.adt.SerializableOld;


public class Transmission extends SerializableOld<Transmission> {

    public static final byte UNIVERSAL_ID = (byte) 0xAA;

    private byte transmissionType;
    private byte targetSpecifier;


    private Packet[] packets;


    public static <T extends TransmissionContent<T>> Transmission create(T content, int packetSizeLimit) {
        Transmission transmission = new Transmission();

        return transmission;
    }




    @Override
    public void byteify(byte[] dest, int[] pointer) {

    }

    public Transmission recreate(byte[] data) {
        return recreate(data,new int[1]);
    }

    @Override
    public Transmission recreate(byte[] data, int[] pointer) {
        return null;
    }

    @Override
    public int headerSize() {
        return 0;
    }

    @Override
    public int dataSize() {
        return 0;
    }



    public enum TransmissionType {

        NULL,
        CONNECT,
        DISCONNECT,
        RESPONSE,
        DATABASE,
        MESSAGE,
        FILE,

    }

    public enum TargetSpecifier {

        NULL,
        SERVER,
        CLIENT,
        GROUP,
        GLOBAL
    }
}
