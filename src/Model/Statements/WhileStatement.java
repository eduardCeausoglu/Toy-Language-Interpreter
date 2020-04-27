package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class WhileStatement implements IStatement {

    private Expression exp;
    private IStatement statement;

    public WhileStatement(Expression exp, IStatement statement) {
        this.exp = exp;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "(while(" + exp.toString() + ")" + statement.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        IDictionary<String, Value> symbolTable = state.getSymbolTable();
        Value result = exp.evaluate(symbolTable,state.getHeap());
        if (result.getType().equals(new BoolType())){
            BoolValue downcastedResult = (BoolValue)result;
            if (downcastedResult.getValue()){
                state.getExecutionStack().push(new WhileStatement(exp,statement));
                state.getExecutionStack().push(statement);
            }
        }
        else
            throw new MyException("Condition exp is not a boolean.");
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        IDictionary<String, Type> newEnv = typeEnv.copy();
        Type type = exp.typeCheck(newEnv);
        if (type.equals(new BoolType()))
            return typeEnv;
        else
            throw new MyException("while stmt: condition expr type is not bool");
    }
}