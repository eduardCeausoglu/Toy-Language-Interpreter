package Controller;

import Model.ADTs.IStack;
import Model.Exceptions.MyException;
import Model.ProgramState.ProgramState;
import Model.Statements.IStatement;
import Model.Statements.IfStatement;
import Model.Statements.NopStatement;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repo;
    private String programState;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void executeOneStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        repo.setPrgList(removeCompletedPrg(repo.getPrgList()));
        List<ProgramState> programStates = repo.getPrgList();
        if(programStates.size() > 0)
        {
            try {
                oneStepForAllPrg(repo.getPrgList());
            } catch (InterruptedException e) {
                System.out.println();
            }
            repo.setPrgList(removeCompletedPrg(repo.getPrgList()));
            executor.shutdownNow();
            callGarbageCollector(programStates);
        }
    }

    public void oneStepForAllPrg(List<ProgramState> programStates) throws InterruptedException, MyException {
        programStates.forEach(p-> {
            try {
                this.repo.logPrgStateExec(p);
            } catch (IOException e) {

            }
        });
        List<Callable<ProgramState>> callableList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(()-> p.executeOneStep()))
                .collect(Collectors.toList());
        List<ProgramState> newProgramStates = executor.invokeAll(callableList)
                .stream()
                .map((Future<ProgramState> future) -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(e -> e != null)
                .collect(Collectors.toList());


        programStates.addAll(newProgramStates);
        programStates.forEach(prog -> {
            try {
                this.repo.logPrgStateExec(prog);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.repo.setPrgList(programStates);
    }

    public void executeAllStep() throws InterruptedException, MyException {
        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStates = removeCompletedPrg(this.repo.getPrgList());
        while(programStates.size()>0){
            callGarbageCollector(programStates);
            oneStepForAllPrg(programStates);
            programStates = removeCompletedPrg(this.repo.getPrgList());
        }
        this.executor.shutdownNow();
        programStates = removeCompletedPrg(this.repo.getPrgList());
        this.repo.setPrgList(programStates);
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void callGarbageCollector(List<ProgramState> programStates){
        programStates.forEach(
                p-> {p.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(p.getSymbolTable().getContent().values(),p.getHeap().getContent().values()),p.getHeap().getContent()));}
        );
    }

    public void addProgram(ProgramState progState){this.repo.addProgram(progState);}

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Collection<Value> heap){
        return  Stream.concat(
                heap.stream()
                        .filter(v-> v instanceof ReferenceValue)
                        .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();}),
                symTableValues.stream()
                        .filter(v-> v instanceof ReferenceValue)
                        .map(v-> {ReferenceValue v1 = (ReferenceValue)v; return v1.getAddress();})
        )
                .collect(Collectors.toList());
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> addressesFromSymbolTable, Map<Integer, Value> heap) {
        return heap.entrySet()
                .stream()
                .filter(e -> addressesFromSymbolTable.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public IRepository getRepo(){return this.repo;}

}