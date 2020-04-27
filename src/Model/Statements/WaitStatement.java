package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.ValueExpression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class WaitStatement implements IStatement{
    private int time;
    private int initialTime;

    public WaitStatement(int x){
        this.time = x;
        this.initialTime=x;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        //IDictionary<String, Value> table = state.getSymbolTable();
        while(this.time>0) {
            PrintStatement s = new PrintStatement(new ValueExpression(new IntValue(this.initialTime-this.time+1)));
            state.getExecutionStack().push(s);
            this.time--;
        }
        return null;
    }

    @Override
    public String toString(){
        return "wait(" + this.time + ")";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
