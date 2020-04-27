package Repository;

import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private int currentIndex;
    private String logFilePath;
    private Boolean ok;

    public Repository(String path) {
        this.programStates = new ArrayList<>();
        this.currentIndex=0;
        this.logFilePath = path;
        this.ok = true;
    }

    @Override
    public void addProgram(ProgramState progState) {
        this.programStates.add(progState);
    }

    @Override
    public void logPrgStateExec(ProgramState prgState) throws IOException {
        PrintWriter writer;
        if (ok)
        {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath,false)));
            ok = false;
        }
        else
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath,true)));
        writer.print(prgState);
        writer.close();
    }

    @Override
    public List<ProgramState> getPrgList(){
        return this.programStates;
    }

    @Override
    public void setPrgList(List<ProgramState> prgStates){
        this.programStates = prgStates;
    }

    @Override
    public ProgramState getProgramById(int id){
        for(ProgramState p : programStates)
            if(p.getId() == id)
                return p;
        return null;
    }
}