package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.VariableExpression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class ForStatement implements IStatement {

    private IStatement i;
    private Expression cmp;
    private IStatement doStatement;
    private IStatement inc;

    public ForStatement(IStatement i, Expression cmp, IStatement doStatement, IStatement inc) {
        this.i = i;
        this.cmp = cmp;
        this.doStatement = doStatement;
        this.inc = inc;
    }

    @Override
    public String toString() {
        return "(for(" + this.i.toString() + ", " + this.cmp.toString()+", " + this.inc.toString() + "){" + this.doStatement.toString() + "}";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        IStatement s = new CompoundStatement(this.i, new WhileStatement(this.cmp, new CompoundStatement(this.doStatement, this.inc)));
        state.getExecutionStack().push(s);
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        /*IDictionary<String, Type> newEnv = typeEnv.copy();
        Type t1 = this.i.typeCheck(newEnv);
        if (t1.equals(new IntType()))
            return typeEnv;
        else
            throw new MyException("int stmt: counter expr type is not int");*/
        return typeEnv;
    }
}