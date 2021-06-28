package it.polimi.ingsw.utils;

import java.io.Serializable;
import java.util.Objects;

/**
 * General class to create an object made by one attribute as a key and a second attribute as a value.
 * This is conceived with the idea of binds the same key with multiple values to build a collection.
 * @param <K> first object type
 * @param <V> second object type.
 */
public class Pair<K,V> implements Serializable {
    private K key;
    private V value;

    /**
     * Constructor that initializes first and second attribute.
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Returns value of the first attribute.
     */
    public K getKey() {
        return key;
    }

    /**
     * Returns value of the second attribute.
     */
    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(key, pair.key) && Objects.equals(value, pair.value);
    }

}
