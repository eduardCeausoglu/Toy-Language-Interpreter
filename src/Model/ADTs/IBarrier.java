package Model.ADTs;

import java.util.Map;

public interface IBarrier<T> {

    String toString();
    int getFreeAddress();
    Map<Integer, T> getBarrierTable();
    void setBarrierTable(Map<Integer, T> bt);
    void put(Integer addr, T value);
}
