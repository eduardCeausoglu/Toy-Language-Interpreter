package Model.Statements.Barrier;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class NewBarrierStatement implements IStatement {
    private String var;
    private Expression exp;

    @Override
    public String toString(){
        return "newBarrier(" + var + "," + exp.toString() + ")";
    }

    public NewBarrierStatement(String var, Expression exp){
        this.var = var;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException{
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Value number1 = exp.evaluate(state.getSymbolTable(), state.getHeap());
        if(!number1.getType().equals(new IntType()))
            throw new MyException("Expression is not of type int");
        int numberResult = ((IntValue)number1).getValue();
        Pair<Integer, List<Integer>> pair = new Pair<Integer, List<Integer>>(numberResult, new ArrayList<Integer>());
        int addr = state.getBarrierTable().getFreeAddress();
        state.getBarrierTable().put(addr, pair);
        state.getSymbolTable().update(var,  new IntValue(addr));
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv)throws MyException{
        Type typeExp = exp.typeCheck(typeEnv);
        Type typeVar = typeEnv.lookup(var);
        if(typeExp.equals(new IntType()) && typeVar.equals(new IntType()))
            return typeEnv;
        throw new MyException("var/exp not of type int");

    }
}
