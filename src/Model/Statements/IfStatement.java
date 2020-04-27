package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;

public class IfStatement implements IStatement {

    private Expression expression;
    private IStatement ifStatement, elseStatement;

    public IfStatement(Expression expression, IStatement ifStatement, IStatement elseStatement) {
        this.expression = expression;
        this.ifStatement = ifStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "if " + this.expression.toString() + " then " + this.ifStatement.toString() + " else " + this.elseStatement.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        Value result = this.expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(result.getType().equals(new IntType())) {
            IntValue v = (IntValue) result;
            int val = v.getValue();
            if(val != 0)
                this.ifStatement.execute(state);
            else
                this.elseStatement.execute(state);
        }
        if(((BoolValue) result).getValue())
            this.ifStatement.execute(state);
        else
            this.elseStatement.execute(state);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typeCheck(typeEnv);
        if(typeExp.equals(new IntType())){
            ifStatement.typeCheck(typeEnv);
            elseStatement.typeCheck(typeEnv);
            return typeEnv.copy();
        }
        if (typeExp.equals(new BoolType())){
            ifStatement.typeCheck(typeEnv);
            elseStatement.typeCheck(typeEnv);
            return typeEnv.copy();
        }
        else
            throw new MyException("if stmt: type is not boolean ");

    }

}