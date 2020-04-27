package Model.Statements.Semaphore;

import Model.ADTs.IDictionary;
import Model.ADTs.ISemaphoreTable;
import Model.ADTs.ITuple;
import Model.ADTs.MySemaphoreTable;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Types.Type;
import Model.Values.IntValue;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStatement implements IStatement {
    private Expression var;

    @Override
    public String toString() {
        return "release(" + var.toString() + ")";
    }

    public ReleaseStatement(Expression var) {
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
        if (entry.getSecond().contains(state.getId()))
        {
            lock.lock();
            entry.getSecond().remove(state.getId());
            semaphoreTable.put(foundIndex,entry);
            state.setSemaphoreTable(semaphoreTable);
            lock.unlock();
        }

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}