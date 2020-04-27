package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;

public interface Expression {
    Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException;
    Type typeCheck(IDictionary<String, Type> typeEnv) throws MyException;
    @Override
    String toString();
}