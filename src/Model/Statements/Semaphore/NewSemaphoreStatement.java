package Model.Statements.Semaphore;

import Model.ADTs.*;
import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.Expressions.VariableExpression;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphoreStatement implements IStatement {
    private VariableExpression var;
    private Expression exp1,exp2;

    public NewSemaphoreStatement(VariableExpression var, Expression exp1, Expression exp2) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public String toString() {
        return "NewSemaphore(" + var.toString() + "," + exp1.toString() + "," + exp2.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        ReentrantLock lock = new ReentrantLock();

        IDictionary<String, Value> symTable = state.getSymbolTable();
        IHeap<Value> heap = state.getHeap();
        ISemaphoreTable semTable = state.getSemaphoreTable();
        Value number1 = exp1.evaluate(symTable,heap);
        Value number2 = exp2.evaluate(symTable,heap);
        int n1 = ((IntValue) number1).getValue();
        int n2 = ((IntValue) number2).getValue();
        int addr = semTable.getSemaphoreAddress();
        semTable.put(addr,new MyTuple(n1,new ArrayList<>(),n2));
        if(symTable.isDefined(var.toString()) && symTable.lookup(var.toString()).getType().equals(new IntType())){
            lock.lock();
            symTable.update(var.toString(),new IntValue(addr));
            lock.unlock();
        }
        else
            throw new MyException("Error when creating a semaphore.");

        return null;
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}