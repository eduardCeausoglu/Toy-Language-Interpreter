package Model.ADTs;

import Model.Exceptions.MyException;

import java.util.List;

public interface IStack<T> {
    T pop() throws MyException;
    void push(T value);
    boolean isEmpty();
    List<T> getValues();

    @Override
    String toString();
}