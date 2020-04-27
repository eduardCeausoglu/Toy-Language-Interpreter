package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;
import javafx.application.Preloader;

public class ValueExpression implements Expression {
    private Value value;

    public ValueExpression(Value val) {
        this.value = val;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        return value;
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        return value.getType();
    }
}