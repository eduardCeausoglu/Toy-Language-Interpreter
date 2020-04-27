package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class NopStatement implements IStatement{

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public String toString(){
        return "";
    }

    @Override
    public IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment;
    }
}