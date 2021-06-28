package io.serialization.transmission;

import io.serialization.Serializer;
import io.serialization.exceptions.DataRecreationException;
import io.serialization.transmission.old.PacketData;

import java.nio.charset.Charset;

import static io.serialization.Serializer.*;

public class TextMessage extends PacketData<TextMessage> {

    public static final byte PACKET_TYPE = TYPE_TEXT_MESSAGE;
    public static final byte HEADER_SIZE = 7;
    private byte format;

    private byte[] data;


    public TextMessage() {}


    public static TextMessage create(String data, Charset charset) {
        TextMessage textMessage = new TextMessage();
        textMessage.format = Serializer.charsetToByte(charset);
        textMessage.data = data.getBytes(charset);
        return textMessage;
    }

    public String getString() {
        return new String(data, Serializer.byteToCharset(format));
    }

    @Override
    public void byteify(byte[] dest, int[] pointer) {
        writeBytes(dest,pointer, PACKET_HEADER);
        writeBytes(dest,pointer,PACKET_TYPE);
        writeBytes(dest,pointer,size());
        writeBytes(dest,pointer,format);
        writeBytes(dest,pointer,data);
    }

    public TextMessage recreate(byte[] data) {
        return recreate(data,new int[1]);
    }

    @Override
    public TextMessage recreate(byte[] data, int[] pointer) throws DataRecreationException{

        if (readByte(data,pointer) != PACKET_HEADER)
            throw new DataRecreationException("Invalid Common Header");

        if (readByte(data,pointer) != PACKET_TYPE)
            throw new DataRecreationException("Invalid PacketType");

        int size = readInt(data,pointer);
        this.data = new byte[size - headerSize()];
        format = readByte(data, pointer);
        readByteArray(data,pointer,this.data);

        return this;
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
