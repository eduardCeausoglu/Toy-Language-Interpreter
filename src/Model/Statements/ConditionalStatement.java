package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class ConditionalStatement implements IStatement{
    private Expression exp1;
    private Expression exp2;
    private Expression exp3;
    private String id;

    public ConditionalStatement(String id, Expression e1 ,Expression e2, Expression e3){
        this.exp1 = e1;
        this.exp2 = e2;
        this.exp3 = e3;
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        IfStatement s = new IfStatement(exp1, new AssignmentStatement(id, exp2), new AssignmentStatement(id, exp3));
        state.getExecutionStack().push(s);
        return null;
    }

    @Override
    public String toString(){
        return this.id + "=" + this.exp1.toString() + "?" + this.exp2.toString() + ":" + this.exp3.toString();
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

}
