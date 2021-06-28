package test;

import io.serialization.database.Database;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class RndDatabase {

    public static Random rnd = new Random();

    public static int rndInt(int min, int max) {
        return  rnd.nextInt((max - min) + 1) + min;
    }


    public static Database create() {


        Database database = new Database(varName());

        for (int i = 0; i < rndInt(3,8); i++) {

            database.add(RndObject.create());
        }

        return database;
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
