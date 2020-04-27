package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.List;
import java.util.Map;

public interface IDictionary<K,V> {

    public V lookup(K key) throws MyException;
    boolean isDefined(K key);
    void update(K key, V value);
    void delete(K key) throws MyException;
    Map<K,V> getContent();
    public IDictionary<K,V> copy();

    @Override
    String toString();
}