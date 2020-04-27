package Model.Statements;

import Model.ADTs.IDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException, IOException;
    IDictionary<String, Type> typeCheck(IDictionary<String, Type> typeEnvironment) throws MyException;
    @Override
    String toString();
}