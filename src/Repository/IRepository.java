package Repository;

import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import java.io.IOException;
import java.util.List;

public interface IRepository {
    public void addProgram(ProgramState progState);
    public void logPrgStateExec(ProgramState prgState) throws IOException;
    public List<ProgramState> getPrgList();
    public void setPrgList(List<ProgramState> prgStates);
    ProgramState getProgramById(int id);
}