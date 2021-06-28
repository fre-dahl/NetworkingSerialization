package io.serialization.old;

import io.serialization.exceptions.DataRecreationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.serialization.Serializer.*;

public class DB_Object extends DB_Entry<DB_Object> {

    private static final byte CONTAINER_TYPE = DB_Entry.OBJECT;

    private byte  fieldCount    = 0;
    private byte  stringCount   = 0;
    private byte  arrayCount    = 0;
    private short objectCount   = 0;

    private final List<DB_Field>   fields   = new ArrayList<>();
    private final List<DB_String>  strings  = new ArrayList<>();
    private final List<DB_Array>   arrays   = new ArrayList<>();
    private final List<DB_Object>  objects  = new ArrayList<>();

    private Map<String, DB_Object> objectMap;
    private boolean usingMap;

    public DB_Object() {}

    public static DB_Object create(String name) {
        DB_Object object = new DB_Object();
        object.incHeaderSize(10);
        object.setName(name);
        return object;
    }

    public <T extends DB_Entry<T>>void add(T serializable) {

        if (serializable instanceof DB_Field) {
            fields.add((DB_Field) serializable);
            incDataSize(serializable.size());
            fieldCount++;
        }
        else if (serializable instanceof DB_String) {
            strings.add((DB_String) serializable);
            incDataSize(serializable.size());
            stringCount++;
        }
        else if (serializable instanceof DB_Array) {
            arrays.add((DB_Array) serializable);
            incDataSize(serializable.size());
            arrayCount++;
        }
        else if (serializable instanceof DB_Object) {
            objects.add((DB_Object) serializable);
            incDataSize(serializable.size());
            objectCount++;
        }
    }

    public DB_Object findObject(String key) {
        if (usingMap) {
            return objectMap.get(key);
        }
        for (DB_Object object : objects) {
            if (object.name().equals(key))
                return object;
        }
        return null;
    }

    public DB_Array findArray(String key) {
        for (DB_Array array : arrays) {
            if (array.name().equals(key))
                return array;
        }
        return null;
    }

    public DB_String findString(String key) {
        for (DB_String string : strings) {
            if (string.name().equals(key))
                return string;
        }
        return null;
    }

    public DB_Field findField(String key) {
        for (DB_Field field : fields) {
            if (field.name().equals(key))
                return field;
        }
        return null;
    }

    @Override
    public void byteify(byte[] dest, int[] pointer) {

        writeBytes(dest,pointer,CONTAINER_TYPE);
        writeBytes(dest,pointer,size());
        writeBytes(dest,pointer,nameSize);
        writeBytes(dest,pointer,nameData);

        writeBytes(dest,pointer,fieldCount);
        for (DB_Field field : fields)
            field.byteify(dest,pointer);

        writeBytes(dest,pointer,stringCount);
        for (DB_String string : strings)
            string.byteify(dest,pointer);

        writeBytes(dest,pointer,arrayCount);
        for (DB_Array array : arrays)
            array.byteify(dest,pointer);

        writeBytes(dest,pointer,objectCount);
        for (DB_Object object : objects)
            object.byteify(dest,pointer);
    }

    @Override
    public DB_Object recreate(byte[] data, int[] pointer) throws DataRecreationException{

        if (readByte(data,pointer) != CONTAINER_TYPE)
            throw new DataRecreationException("Unmatching Serializable Type");

        int size = readInt(data,pointer);
        nameSize = readByte(data,pointer);
        incHeaderSize(nameSize + 11);
        incDataSize(size - headerSize());
        nameData = new byte[nameSize];
        readByteArray(data,pointer, nameData);

        fieldCount = readByte(data, pointer);

        for (int i = 0; i < fieldCount; i++) {
            DB_Field field = new DB_Field().recreate(data,pointer);
            fields.add(field);
        }

        stringCount = readByte(data, pointer);

        for (int i = 0; i < stringCount; i++) {
            DB_String string = new DB_String().recreate(data,pointer);
            strings.add(string);
        }

        arrayCount = readByte(data, pointer);

        for (int i = 0; i < arrayCount; i++) {
            DB_Array array = new DB_Array().recreate(data,pointer);
            arrays.add(array);
        }

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
