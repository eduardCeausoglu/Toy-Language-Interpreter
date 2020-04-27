package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

import java.net.Proxy;

public class HeapReadExpression implements Expression {
    private Expression exp;

    public HeapReadExpression(Expression exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "rH("  + exp.toString() + ")";
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException {
        Value evaluationValue = this.exp.evaluate(table,heap);
        if (evaluationValue instanceof ReferenceValue){
            //downcast to ref value first
            int address = ((ReferenceValue) evaluationValue).getAddress();
            Value valueFromHeap = heap.get(address);
            if (valueFromHeap!=null){
                return valueFromHeap;
            }
            else
                throw new MyException("Address doesn't have a value.");

        }
        else
            throw new MyException("Value is not of type reference value.");
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Type typ = exp.typeCheck(typeEnvironment);
        if (typ instanceof ReferenceType){
            return ((ReferenceType)typ).getInner();
        }
        else
            throw new MyException("Read Heap argument not Ref Type.");
    }
}