package Model.ADTs;

import javafx.util.Pair;

import java.util.List;

public interface ISemaphoreTable {
    void setSemaphoreTable(IDictionary<Integer, ITuple> sem);
    IDictionary<Integer, ITuple> getSemaphoreTable();
    int getSemaphoreAddress();
    void put(int index, ITuple listPair);
    String toString();
}