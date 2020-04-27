package Model.Statements.Latch;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStatement implements IStatement {
    private String var;
    private Expression exp;

    @Override
    public String toString(){
        return "newLatch(" + this.var + "," + this.exp.toString() + ")";
    }

    public NewLatchStatement(String var, Expression exp){
        this.var = var;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException{
        Value expResult = this.exp.evaluate(state.getSymbolTable(), state.getHeap());
        int nr = ((IntValue)expResult).getValue();
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        int addr = state.getLatchTable().getFreeAddress();
        state.getLatchTable().put(addr, nr);
        state.getSymbolTable().update(var, new IntValue(addr));
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException{
        return typeEnv;
    }
}
