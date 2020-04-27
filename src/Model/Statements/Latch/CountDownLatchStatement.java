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

public class CountDownLatchStatement implements IStatement {

    private String var;

    @Override
    public String toString(){
        return "countDown(" + this.var + ")";
    }

    public CountDownLatchStatement(String var){
        this.var = var;
    }

    @Override
    public ProgramState execute (ProgramState state) throws MyException, IOException{
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Value index = state.getSymbolTable().lookup(var);
        int foundIndex = ((IntValue)index).getValue();
        if(state.getLatchTable().getLatchTable().get(foundIndex)>0){
            state.getLatchTable().getLatchTable().put(foundIndex, state.getLatchTable().getLatchTable().get(foundIndex)-1);
            state.getOutput().add(new IntValue(state.getId()));
        }
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException{
        return typeEnv;
    }
}
