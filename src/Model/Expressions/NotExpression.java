package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class NotExpression implements Expression {
    private Expression exp;

    public NotExpression(Expression expression) {
        this.exp = expression;
    }

    @Override
    public String toString() {
        return this.exp.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        boolean val = ((BoolValue)this.exp.evaluate(table, heap)).getValue();

        return new BoolValue(!val);
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Type type1 = exp.typeCheck(typeEnvironment);

        if(!type1.equals(new BoolType()))
            throw new MyException("non-boolean value.");


        return new BoolType();
    }
}
