package io.serialization.old.adt;


public interface Byteify<T> {


    int size();

    default byte[] getBytes() {
        byte[] data = new byte[6];
        byteify(data,new int[1]);
        return data;
    }
    void byteify(byte[] dest, int[] pointer);

    T recreate(byte[] data, int[] pointer);


}
