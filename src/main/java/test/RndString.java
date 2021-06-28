package test;

import io.serialization.Serializer;
import io.serialization.database.DBString;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RndString {

    public static Random rnd = new Random();

    public static int rndInt(int min, int max) {
        return  rnd.nextInt((max - min) + 1) + min;
    }


    public static DBString create() {
        Charset charset = Serializer.byteToCharset((byte) rndInt(0,4));
        return new DBString(varName(),rndString(charset),charset);
    }


    public static String varName() {

        int length = rndInt(1,10);
        byte[] chars = new byte[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (byte)(rndInt(65,122));
        }

        return new String(chars, StandardCharsets.UTF_8);
    }

    public static String rndString(Charset charset) {

        int length = rndInt(1,50);
        byte[] chars = new byte[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (byte)(rndInt(65,122));
        }

        return new String(chars, charset);
    }
}
