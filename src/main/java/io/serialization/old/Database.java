package io.serialization.old;

import io.serialization.Serializer;
import io.serialization.exceptions.DataRecreationException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.serialization.Serializer.*;

public class Database extends DB_Entry<Database> {

    public static final String SYSTEM = "V2DB";
    public static final byte[] HEADER = SYSTEM.getBytes(StandardCharsets.UTF_8);
    public static final short VERSION = 0x0100;
    private static final byte CONTAINER_TYPE = DB_Entry.DATABASE;

    private short objectCount = 0;

    private final List<DB_Object> objects  = new ArrayList<>();
    private Map<String, DB_Object> objectMap;
    private boolean usingMap;


    public Database() {}

    public static Database create(String name) {
        Database database = new Database();
        database.incHeaderSize(HEADER.length + 9);
        database.setName(name);
        return database;
    }

    public void add(DB_Object object) {
        objects.add(object);
        incDataSize(object.size());
        objectCount++;
    }

    public DB_Object findObject(String key) {
        if (usingMap) return objectMap.get(key);
        for (DB_Object object : objects) {
            if (object.name().equals(key))
                return object;
        }
        return null;
    }

    public void serializeToFile(String path) {
        try {
            FileOutputStream stream = new FileOutputStream(path);
            stream.write(getBytes());
            stream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Database deserializeFromFile(String path) {
        byte[] data = null;
        try {
            FileInputStream stream = new FileInputStream(path);
            data = new byte[stream.available()];
            int bytes = stream.read(data);
            stream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return recreate(data,new int[1]);
    }

    @Override
    public void byteify(byte[] dest, int[] pointer) {

        writeBytes(dest, pointer, HEADER);
        writeBytes(dest, pointer, VERSION);
        writeBytes(dest, pointer, CONTAINER_TYPE);
        Serializer.writeBytes(dest,pointer,size());
        Serializer.writeBytes(dest,pointer,nameSize);
        Serializer.writeBytes(dest,pointer,nameData);

        writeBytes(dest, pointer, objectCount);
        for (DB_Object object : objects)
            object.byteify(dest,pointer);
    }

    @Override
    public Database recreate(byte[] data, int[] pointer) throws DataRecreationException{

        if (!readString(data,pointer, HEADER.length, StandardCharsets.UTF_8).equals(SYSTEM))
            throw new DataRecreationException("Unknown DataBase");

        if (readShort(data, pointer) != VERSION)
            throw new DataRecreationException("UnMatching Version");

        if (readByte(data,pointer) != CONTAINER_TYPE)
            throw new DataRecreationException("UnMatching Serializable Type");

        int size = readInt(data,pointer);
        nameSize = readByte(data,pointer);
        incHeaderSize(nameSize + 10 + HEADER.length);
        incDataSize(size - headerSize());
        nameData = new byte[nameSize];
        readByteArray(data,pointer, nameData);

        objectCount = readShort(data, pointer);

        usingMap = objectCount > 10;

        if (usingMap) {
            objectMap = new HashMap<>();
            for (int i = 0; i < objectCount; i++) {
                DB_Object object = new DB_Object().recreate(data,pointer);
                objectMap.put(object.name(),object);
            }
        }
        else {
            for (int i = 0; i < objectCount; i++) {
                DB_Object object = new DB_Object().recreate(data,pointer);
                objects.add(object);
            }
        }
        return this;
    }
}
