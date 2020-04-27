package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements IDictionary {
    private HashMap<K,V> dictionary;

    public MyDictionary(){
        dictionary = new HashMap<K,V>();
    }

    @Override
    public String toString() {
        String result ="{";
        for (K key : dictionary.keySet())
            result+= key.toString() + " -> " + dictionary.get(key).toString() + ";";
        result+="}";
        return result;
    }

    @Override
    public Object lookup(Object key) throws MyException {
        if (!dictionary.containsKey((K) key))
            throw new MyException("Key doesn't exist.");
        return dictionary.get((K) key);
    }

    @Override
    public boolean isDefined(Object key) {
        return dictionary.containsKey((K) key);
    }

    @Override
    public void update(Object key, Object value) {
        dictionary.put((K) key, (V) value);
    }

    @Override
    public void delete(Object key) throws MyException {
        if (!dictionary.containsKey((K)key))
            throw new MyException("Key doesn't exist.");
        dictionary.remove((K)key);

    }

    @Override
    public IDictionary<K,V> copy() {
        IDictionary<K,V> copy = new MyDictionary<>();
        for (K k : dictionary.keySet()) {
            copy.update(k,dictionary.get(k));
        }
        return copy;
    }

    @Override
    public Map getContent() {
        return dictionary;
    }
}