package Model.ADTs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyBarrier<T> implements IBarrier<T>{

    private int addr;
    private ConcurrentHashMap<Integer, T> barrierTable;

    public MyBarrier(){
        this.barrierTable = new ConcurrentHashMap<>();
    }

    @Override
    public int getFreeAddress(){
        return this.addr;
    }

    @Override
    public Map getBarrierTable(){
        return this.barrierTable;
    }

    @Override
    public void put (Integer addr, Object value){
        barrierTable.put(addr, (T)value);
    }

    @Override
    public void setBarrierTable(Map<Integer, T> bt){
        barrierTable = (ConcurrentHashMap<Integer, T>) bt;
    }

    @Override
    public String toString(){
        String s="{";
        for(int key: barrierTable.keySet()){
            s+=key + "->" + barrierTable.get(key).toString() + "\n";
        }
        return s+"}";
    }



}
