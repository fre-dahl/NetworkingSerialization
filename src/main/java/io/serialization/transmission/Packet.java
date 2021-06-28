package io.serialization.transmission;

import io.serialization.old.adt.SerializableOld;

public class Packet extends SerializableOld<Packet> {

    private byte[] data;

    @Override
    public void byteify(byte[] dest, int[] pointer) {

    }

    @Override
    public Packet recreate(byte[] data, int[] pointer) {
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
}
