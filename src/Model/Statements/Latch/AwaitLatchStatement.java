package Model.Statements.Latch;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitLatchStatement implements IStatement {

    private String var;

    @Override
    public String toString(){
        return "await(" + this.var.toString() + ")";
    }

    public AwaitLatchStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException{
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        if(state.getSymbolTable().isDefined(this.var)){
            Value index = state.getSymbolTable().lookup(this.var);
            int foundIndex = ((IntValue)index).getValue();
            if(state.getLatchTable().isDefined(foundIndex)){
                if(state.getLatchTable().getLatchTable().get(foundIndex)!=0)
                    state.getExecutionStack().push(new AwaitLatchStatement(this.var));
            }
            else throw new MyException("index not in latchTable");
        }
        else throw new MyException("var is not in symtable");
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv)throws MyException{
        return typeEnv;
    }
}
