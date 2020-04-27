package Model.Statements.Semaphore;

import Model.ADTs.IDictionary;
import Model.ADTs.ISemaphoreTable;
import Model.ADTs.ITuple;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Types.Type;
import Model.Values.IntValue;
import javafx.application.Application;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStatement implements IStatement {
    private Expression var;

    @Override
    public String toString() {
        return "acquire(" + var.toString() + ")";
    }

    public AcquireStatement(Expression var) {
        this.var = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        ReentrantLock lock = new ReentrantLock();
        int foundIndex = ((IntValue)state.getSymbolTable().lookup(var.toString())).getValue();
        ISemaphoreTable semaphoreTable = state.getSemaphoreTable();
        if (!semaphoreTable.getSemaphoreTable().isDefined(foundIndex))
            throw new MyException("no such index in the sem table");
        ITuple entry = semaphoreTable.getSemaphoreTable().lookup(foundIndex);
        int nL = entry.getSecond().size();
        int n1 = entry.getFirst();
        int n2 = entry.getThird();
        lock.lock();
        if(Math.abs(n1-n2)>nL) {
            if (entry.getSecond().contains(state.getId())) {
                System.out.println();
            } else {
                entry.getSecond().add(state.getId());
                semaphoreTable.put(foundIndex,entry);
                state.setSemaphoreTable(semaphoreTable);
            }
        }
        else
            state.getExecutionStack().push(new AcquireStatement(var));
        lock.unlock();
        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}