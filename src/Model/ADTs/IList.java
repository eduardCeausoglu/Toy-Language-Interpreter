package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.List;

public interface IList<T> {
    void add(T item);
    void remove(T item) throws MyException;
    T get(int index) throws MyException;
    int size();
    List getContent();

    @Override
    String toString();
}