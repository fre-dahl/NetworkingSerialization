package io.serialization.old.adt;


public abstract class SerializableOld<T extends SerializableOld<T>> extends Data<T> {

    @Override
    public int size() {
        return headerSize() + dataSize();
    }

    public abstract int headerSize();

    public abstract int dataSize();
}
