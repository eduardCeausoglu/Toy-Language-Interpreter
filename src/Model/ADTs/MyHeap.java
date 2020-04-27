package Model.ADTs;

import Model.Types.Type;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<T> implements IHeap<T> {
    private HashMap<Integer, T> map;
    private int memory;

    public MyHeap() {
        this.map = new HashMap<Integer,T>();
        this.memory = 0;
    }

    @Override
    public int allocate(Object val) {
        this.memory++;
        this.map.put(this.memory,(T)val);
        return this.memory;
    }

    @Override
    public T get(int addr) {
        return this.map.get(addr);
    }

    @Override
    public void put(int addr, Object val) {
        this.map.put(addr,(T)val);
    }

    @Override
    public T deallocate(int addr) {
        return this.map.remove(addr);
    }

    @Override
    public Map<Integer, T> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer, T> content) {
        this.map = (HashMap)content;
    }

    @Override
    public String toString() {
        String s="{";
        for(HashMap.Entry<Integer,T> entry : this.map.entrySet()){
            s+=entry.getKey().toString() + "->" + entry.getValue().toString() + "\n";
        }
        s+="}";
        return s;
    }
}