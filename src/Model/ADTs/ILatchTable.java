package Model.ADTs;

import java.util.Map;

public interface ILatchTable<K, V> {
    void put(K key, V value);
    void setLatchTable(Map<K, V> newTable);
    Map<K, V> getLatchTable();
    boolean isDefined(K key);
    int getFreeAddress();
}
