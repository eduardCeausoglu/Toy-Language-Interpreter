package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;


public class MulExpression implements Expression{

    private Expression exp1;
    private Expression exp2;

    public MulExpression(Expression e1, Expression e2){
        this.exp1 = e1;
        this.exp2 = e2;
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException{
        ArithmeticExpression e1 = new ArithmeticExpression('*', this.exp1, this.exp2);
        ArithmeticExpression e2 = new ArithmeticExpression('+', this.exp1, this.exp2);
        ArithmeticExpression e = new ArithmeticExpression('-', e1, e2);
        return (IntValue)e.evaluate(table, heap);
    }

    @Override
    public String toString(){
        return "MUL(" + this.exp1.toString() + ", " + this.exp2.toString() + ")";
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        /*Type t1, t2;
        t1 = this.exp1.typeCheck(typeEnvironment);
        t2 = this.exp2.typeCheck(typeEnvironment);
        if(!t1.equals(new IntType())) throw new MyException("First operand is not an integer!");
        if(!t2.equals(new IntType())) throw new MyException("Second operand is not an integer!");
        return new IntType();*/
        return null;
    }

}
