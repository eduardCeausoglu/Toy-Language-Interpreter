package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.NotExpression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class RepeatUntilStatement implements IStatement{

    private Expression exp;
    private IStatement stmt;

    public RepeatUntilStatement(IStatement s, Expression e) {
        this.stmt = s;
        this.exp = e;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement s = new CompoundStatement(this.stmt, new WhileStatement(new NotExpression(this.exp), this.stmt));
        state.getExecutionStack().push(s);
        return null;
    }

    @Override
    public String toString(){
        return "(repeat " + this.stmt.toString() + ") until " + this.exp.toString() + ")";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
