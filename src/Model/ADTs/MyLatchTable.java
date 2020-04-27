package Model.ADTs;

import java.util.Map;
import java.util.HashMap;

public class MyLatchTable<K, V> implements ILatchTable<K, V>{
    private HashMap<K, V> latchTable;
    private int addr;

    @Override
    public String toString(){
        String s = "{";
        for (Map.Entry entry: latchTable.entrySet()){
            s+=entry.getKey() + "->" + entry.getValue() + "\n";
        }
        return s+"}";
    }

    public MyLatchTable(){
        this.latchTable = new HashMap<>();
        this.addr = 0;
    }

    @Override
    public void put(K key, V value){
        this.latchTable.put(key, value);
    }

    @Override
    public void setLatchTable(Map<K, V> newTable){
        this.latchTable = (HashMap<K, V>) newTable;
    }

    @Override
    public Map<K, V> getLatchTable(){
        return this.latchTable;
    }

    @Override
    public boolean isDefined(K key){
        return this.latchTable.containsKey(key);
    }

    @Override
    public int getFreeAddress(){
        return this.addr++;
    }
}
