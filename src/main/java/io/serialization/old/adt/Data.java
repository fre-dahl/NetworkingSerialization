package io.serialization.old.adt;

public abstract class Data<T extends Data<T>> implements Byteify<T>{

    abstract public int size();

}
