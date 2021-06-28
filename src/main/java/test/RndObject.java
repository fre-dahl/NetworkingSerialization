package test;

import io.serialization.database.DBObject;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RndObject {

    public static Random rnd = new Random();

    public static int rndInt(int min, int max) {
        return  rnd.nextInt((max - min) + 1) + min;
    }

    public static DBObject create() {

        DBObject object = new DBObject(varName());

        for (int i = 0; i < rnd.nextInt(5); i++) {
            object.add(RndArray.create());
        }

        for (int i = 0; i < rnd.nextInt(5); i++) {
            object.add(RndField.create());
        }

        for (int i = 0; i < rnd.nextInt(5); i++) {
            object.add(RndString.create());
        }

        //if (rnd.nextBoolean()) object.add(RndObject.create());


        return object;
    }

    public static String varName() {

        int length = rndInt(1,10);
        byte[] chars = new byte[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (byte)(rndInt(65,122));
        }

        return new String(chars, StandardCharsets.UTF_8);
    }
}
