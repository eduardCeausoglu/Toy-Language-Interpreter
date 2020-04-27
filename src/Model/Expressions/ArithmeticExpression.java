package Model.Expressions;

import Model.ADTs.IDictionary;
import Model.ADTs.IHeap;
import Model.Exceptions.MyException;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithmeticExpression implements Expression {
    private Expression exp1,exp2;
    private char op;

    public ArithmeticExpression(char op,Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public String toString() {
        return this.exp1.toString()  + this.op  + this.exp2.toString();
    }

    @Override
    public Value evaluate(IDictionary<String, Value> table, IHeap<Value> heap) throws MyException{
        Value value1,value2;
        value1 = exp1.evaluate(table,heap);
        if(value1.getType().equals(new IntType())){
            value2 = exp2.evaluate(table,heap);
            if(value2.getType().equals(new IntType())){
                IntValue toInteger1 = (IntValue)value1;
                IntValue toInteger2 = (IntValue)value2;
                int number1,number2;
                number1 = toInteger1.getValue();
                number2 = toInteger2.getValue();
                switch (op) {
                    case '+':
                        return new IntValue(number1+number2);
                    case '-':
                        return new IntValue(number1-number2);
                    case '*':
                        return new IntValue(number1*number2);
                    case '/':
                        if (number2==0)
                            throw new MyException("Division by zero!");
                        return new IntValue(number1/number2);
                    default:
                        throw new MyException("Invalid operand");
                }
            }
            else
                throw new MyException("Second operand is not an int.");
        }
        else throw new MyException("First operand is not an int.");
    }

    @Override
    public Type typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        Type type1, type2;
        type1 = exp1.typeCheck(typeEnvironment);
        type2 = exp2.typeCheck(typeEnvironment);
        if(!type1.equals(new IntType())) throw new MyException("First operand is not an integer!");
        if(!type2.equals(new IntType())) throw new MyException("Second operand is not an integer!");
        return new IntType();
    }
}