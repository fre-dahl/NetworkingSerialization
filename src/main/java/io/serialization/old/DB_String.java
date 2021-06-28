package io.serialization.old;

import io.serialization.Serializer;
import io.serialization.exceptions.DataRecreationException;

import java.nio.charset.Charset;

import static io.serialization.Serializer.*;

public class DB_String extends DB_Entry<DB_String> {

    private static final byte CONTAINER_TYPE = DB_Entry.STRING;
    private byte format;
    private byte[] data;

    public DB_String() {}

    public static DB_String create(String name, String data, Charset charset) {
        DB_String string = new DB_String();
        string.setName(name);
        string.incHeaderSize(6);
        string.format = Serializer.charsetToByte(charset);
        string.data = data.getBytes(charset);
        string.incDataSize(string.data.length);
        return string;
    }

    public String getString() {
        return new String(data, Serializer.byteToCharset(format));
    }

    @Override
    public void byteify(byte[] dest, int[] pointer) {
        writeBytes(dest,pointer,CONTAINER_TYPE);
        writeBytes(dest,pointer,size());
        writeBytes(dest,pointer,nameSize);
        writeBytes(dest,pointer,nameData);
        writeBytes(dest,pointer,format);
        writeBytes(dest,pointer,data);
    }

    @Override
    public DB_String recreate(byte[] data, int[] pointer) throws DataRecreationException{

        if (readByte(data,pointer) != CONTAINER_TYPE)
            throw new DataRecreationException("Unmatching Serializable Type");

        int size = readInt(data,pointer);
        nameSize = readByte(data,pointer);
        incHeaderSize(nameSize + 7);
        incDataSize(size - headerSize());
        this.data = new byte[dataSize()];
        nameData = new byte[nameSize];
        readByteArray(data,pointer, nameData);
        format = readByte(data,pointer);
        readByteArray(data,pointer,this.data);

        return this;
    }
}
