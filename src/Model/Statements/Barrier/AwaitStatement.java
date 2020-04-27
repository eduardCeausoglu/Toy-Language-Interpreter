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
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStatement implements IStatement {

    private Expression var;

    @Override
    public String toString(){
        return "await(" + var.toString() + ")";
    }

    public AwaitStatement(Expression var){
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        Value found = state.getSymbolTable().lookup(var.toString());
        if(!found.getType().equals(new IntType()))
            throw new MyException("var is not of type int");
        int foundIndex = ((IntValue)found).getValue();
        if(!state.getBarrierTable().getBarrierTable().containsKey(foundIndex))
            throw new MyException("no index found in barrier table");
        Pair<Integer, List<Integer>> pair = state.getBarrierTable().getBarrierTable().get(foundIndex);
        int NL = pair.getValue().size();
        int n1 = pair.getKey();
        List<Integer> threads = pair.getValue();
        if(n1>NL){
            if(pair.getValue().contains(state.getId())){
                state.getExecutionStack().push(new AwaitStatement(var));
            }
            else{
                threads.add(state.getId());
                state.getBarrierTable().getBarrierTable().put(foundIndex, new Pair(n1, threads));
                state.getExecutionStack().push(new AwaitStatement(var));
            }
        }
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException{
        Type typeExp = var.typeCheck(typeEnv);
        if(typeExp.equals(new IntType()))
            return typeEnv;
        throw new MyException("var is not of type int");
    }

}
