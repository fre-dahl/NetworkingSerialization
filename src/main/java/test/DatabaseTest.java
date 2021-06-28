package test;

import io.serialization.database.*;

import java.io.IOException;

public class DatabaseTest {

    public static void main(String[] args) {

        //Logger logger = Logger.getLogger("");

        Database source = new Database("DB");
        Database destination;

        DBObject object = new DBObject("entity");
        DBObject object2 = new DBObject("entity2");

        DBField field = new DBField("value1",33);
        DBField field2 = new DBField("value2",46);

        DBString string = new DBString("string","Frederik Dahl");
        DBString string2 = new DBString("string","Car");

        DBArray array = new DBArray("array",new boolean[8]);

        object.add(field);
        object.add(field2);
        object.add(string);
        object.add(string2);
        object.add(array);

        object2.add(field);
        object2.add(field2);
        object2.add(string);
        object2.add(string2);
        object2.add(object);
        object2.add(array);

        source.add(object);
        source.add(object2);


        try {
            source.serializeToFile("data3.bin");
            destination = Database.deserializeFromFile("data3.bin");
            System.out.println(destination.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
