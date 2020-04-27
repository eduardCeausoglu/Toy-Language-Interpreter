package Model.Statements;

import Model.ADTs.IDictionary;
import Model.ADTs.MyStack;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

import java.io.IOException;

public class ForkStatement implements IStatement {

    private IStatement stmt;

    public ForkStatement(IStatement s)
    {
        this.stmt=s;
    }

    @Override
    public String toString(){return "fork("+this.stmt.toString()+")";}

    @Override
    public ProgramState execute(ProgramState state) throws MyException, IOException {
        return new ProgramState(new MyStack<>(), state.getSymbolTable().copy(), state.getOutput(),state.getFileTable(),this.stmt, state.getHeap(), state.getLatchTable(), state.getBarrierTable(), state.getSemaphoreTable());
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        stmt.typeCheck(typeEnvironment.copy());
        return typeEnvironment;
    }
}
